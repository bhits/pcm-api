/*jshint browser:true*/

//
// jquery.sessionTimeout.js
//
// After a set amount of time, a dialog is shown to the user with the option
// to either log out now, or stay connected. If log out now is selected,
// the page is redirected to a logout URL. If stay connected is selected,
// a keep-alive URL is requested through AJAX. If no options is selected
// after another set amount of time, the page is automatically redirected
// to a timeout URL.
//
//

/*
 * Override JQuery.ui.dialog.open function 
 * This fixes a problem where the focus shifts out of the dialog and doesn't shift back when using the tab key.
 * By listening for a keydown event for TAB key instead of a keypress event, the problem is fixed.
 * Modified by Jiahao Li
*/
(function($){
	jQuery.ui.dialog.prototype.open = function() {
		if (this._isOpen) { return; }

		var self = this,
			options = self.options,
			uiDialog = self.uiDialog;

		self.overlay = options.modal ? new $.ui.dialog.overlay(self) : null;
		self._size();
		self._position(options.position);
		uiDialog.show(options.show);
		self.moveToTop(true);

		// prevent tabbing out of modal dialogs
		if (options.modal) {
			uiDialog.bind('keydown.ui-dialog', function(event) {
				if (event.keyCode !== $.ui.keyCode.TAB) {
					return;
				}

				var tabbables = $(':tabbable', this),
					first = tabbables.filter(':first'),
					last  = tabbables.filter(':last');

				if (event.target === last[0] && !event.shiftKey) {
					first.focus(1);
					return false;
				} else if (event.target === first[0] && event.shiftKey) {
					last.focus(1);
					return false;
				}
			});
		}

		// set focus to the first tabbable element in the content area or the first button
		// if there are no tabbable elements, set focus on the dialog itself
		$(self.element.find(':tabbable').get().concat(
			uiDialog.find('.ui-dialog-buttonpane :tabbable').get().concat(
				uiDialog.get()))).eq(0).focus();

		self._isOpen = true;
		self._trigger('open');

		return self;
	};
})(jQuery);

// USAGE
//
//   1. Include jQuery
//   2. Include jQuery UI (for dialog)
//   3. Include jquery.sessionTimeout.js
//   4. Call $.sessionTimeout(); after document ready
//
//
// OPTIONS
//
//   message
//     Text shown to user in dialog after warning period.
//     Default: 'Your session is about to expire.'
//
//   keepAliveUrl
//     URL to call through AJAX to keep session alive. This resource should do something innocuous that would keep the session alive, which will depend on your server-side platform.
//     Default: '/keep-alive'
//
//   redirUrl
//     URL to take browser to if no action is take after warning period
//     Default: '/timed-out'
//
//   logoutUrl
//     URL to take browser to if user clicks "Log Out Now"
//     Default: '/log-out'
//
//   warnAfter
//     Time in milliseconds after page is opened until warning dialog is opened
//     Default: 900000 (15 minutes)
//
//   redirAfter
//     Time in milliseconds after page is opened until browser is redirected to redirUrl
//     Default: 1200000 (20 minutes)
//
(function($) {
	jQuery.sessionTimeout = function(options) {
		var defaults = {
			message : 'Your session is about to expire.',
			keepAliveUrl : '/consent2share-pg/keep-alive',
			redirUrl : function() {
				//window.location = o.logoutUrl;
				$('#sessionLogout').submit();
			},
			logoutUrl : function() {
				//window.location = o.logoutUrl;
				$('#sessionLogout').submit();
			},
			warnAfter : 900000, // 15 minutes
			redirAfter : 1200000
		// 20 minutes
		};

		// Extend user-set options over defaults
		var o = defaults, dialogTimer, redirTimer;

		if (options) {
			o = $.extend(defaults, options);
		}
				// Create timeout warning dialog
		
		var csrfValue = $("meta[name='_csrf']").attr('content');
		$('body').append(
				'<div title="Session Timeout" id="sessionTimeout-dialog">'
						+ o.message + '</div>'+
						'<form name="sessionLogout" id="sessionLogout" action="'+getContextPath()+'/resources/j_spring_security_logout" method="post">'
						+' <input type="hidden"'+'name="_csrf"'+'value='+csrfValue+'></form>');
		$('#sessionTimeout-dialog').dialog({
			autoOpen : false,
			width : 400,
			modal : true,
			closeOnEscape : false,
			open : function() {
				$(".ui-dialog-titlebar-close").hide();
				$(".modal").modal('hide');
			},
			buttons : {
				// Button one - takes user to logout URL
				"Log Out Now" : function() {
					//window.location = o.logoutUrl;
					$('#sessionLogout').submit();
				},
				// Button two - closes dialog and makes call to keep-alive URL
				"Stay Connected" : function() {
					$(this).dialog('close');

					$.ajax({
						type : 'GET',
						url : o.keepAliveUrl
					});

					// Stop redirect timer and restart warning timer
					controlRedirTimer('stop');
					controlDialogTimer('start');
				}
			}
		});

		function controlDialogTimer(action) {
			switch (action) {
			case 'start':
				// After warning period, show dialog and start redirect timer
				dialogTimer = setTimeout(function() {
					$('#sessionTimeout-dialog').dialog('open');
					controlRedirTimer('start');
				}, o.warnAfter);
				break;

			case 'stop':
				clearTimeout(dialogTimer);
				break;
			}
		}

		function controlRedirTimer(action) {
			switch (action) {
			case 'start':
				// Dialog has been shown, if no action taken during redir
				// period, redirect
				redirTimer = setTimeout(function() {
					//window.location = o.redirUrl;
					$('#sessionLogout').submit();
				}, o.redirAfter - o.warnAfter);
				break;

			case 'stop':
				clearTimeout(redirTimer);
				break;
			}
		}

		// Begin warning period
		controlDialogTimer('start');
	};
})(jQuery);

$(document).ready(function() {
	
	//If it's login page then just keep session alive. Otherwise popup to let user decide.
	
	if (document.body.getAttribute("id")=="index-body"||document.body.getAttribute("id")=="registration-body") {
		setInterval(function() {
			$.ajax({
				type : 'GET',
				url : "/consent2share-pg/keep-alive"
			});
			}, 840000);	//14 minutes
	}
	else{
		$.sessionTimeout({
			warnAfter : 840000,	//14 minutes
			redirAfter : 900000	//15 minutes
		});
	}
});

function getContextPath(){
	return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
}