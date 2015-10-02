/*
 * jQuery Foundation Joyride Plugin 2.0.3
 * http://foundation.zurb.com
 * Copyright 2012, ZURB
 * Free to use under the MIT license.
 * http://www.opensource.org/licenses/mit-license.php
*/

/*jslint unparam: true, browser: true, indent: 2 */

; (function ($, window, undefined) {
    'use strict';

    var defaults = {
        'version': '2.0.3',
        'tipLocation': 'bottom',  // 'top' or 'bottom' in relation to parent
        'nubPosition': 'auto',    // override on a per tooltip bases
        'scroll': true,      // whether to scroll to tips
        'scrollSpeed': 300,       // Page scrolling speed in milliseconds
        'timer': 0,         // 0 = no timer , all other numbers = timer in milliseconds
        'autoStart': false,     // true or false - false tour starts when restart called
        'startTimerOnClick': true,      // true or false - true requires clicking the first button start the timer
        'startOffset': 0,         // the index of the tooltip you want to start on (index of the li)
        'nextButton': true,      // true or false to control whether a next button is used
        'prevButton': true,      // true or false to control whether a previous button is used
        'tipAnimation': 'fade',    // 'pop' or 'fade' in each tip
        'pauseAfter': [],        // array of indexes where to pause the tour after
        'tipAnimationFadeSpeed': 5,         // when tipAnimation = 'fade' this is speed in milliseconds for the transition
        'cookieMonster': false,     // true or false to control whether cookies are used
        'cookieName': 'joyride', // Name the cookie you'll use
        'cookieDomain': false,     // Will this cookie be attached to a domain, ie. '.notableapp.com'
        'tipContainer': 'body',    // Where will the tip be attached
        'postRideCallback': $.noop,    // A method to call once the tour closes (canceled or complete)
        'postStepCallback': $.noop,    // A method to call after each step
        'preStepCallback' : $.noop,    // A method to call before each step
        'affectHeight': true,      // true or false to control whether tip postion and hieght affect the height of the tip container.
        'heightPadding': 0,         // Bottom padding to be used when tips affect height.
        'mode': 'normal',  // mode to present tips in current options (normal | focus)
        'template': { // HTML segments for tip layout
            'link': '<a href="#close" class="joyride-close-tip">X</a>',
            'timer': '<div class="joyride-timer-indicator-wrap"><span class="joyride-timer-indicator"></span></div>',
            'tip': '<div class="joyride-tip-guide"><span class="joyride-nub"></span></div>',
            'wrapper': '<div class="joyride-content-wrapper" role="dialog"></div>',
            'button': '<a href="#" class="joyride-next-tip"></a>',
            'prevbutton': '<a href="#" class="joyride-prev-tip"></a>'
        }
    },

      Modernizr = Modernizr || false,

      methods = {

          init: function (element,opts) {
              var $this = $(element);
              var settings = $this.data(element.id + '-tour-settings') || {};
              if ($.isEmptyObject(settings)) {
                  $this.addClass('joyride-tour');
                  $.extend(true, settings, defaults);
                  $.extend(true, settings, opts);

                  // non configurable settings
                  settings.tourDataName = element.id + '-tour-settings';
                  settings.document = window.document;
                  settings.$document = $(settings.document);
                  settings.$window = $(window);
                  settings.$content_el = $this;
                  settings.isFixed = $(settings.tipContainer).css('position') === 'fixed';
                  settings.$tip_content = $('> li', settings.$content_el);
                  settings.$modal_bg = undefined;
                  settings.paused = false;
                  settings.attempts = 0;
                  
                  settings.next_delay_flag = false;

                  settings.tipLocationPatterns = {
                      top: ['bottom'],
                      bottom: [], // bottom should not need to be repositioned
                      left: ['right', 'top', 'bottom'],
                      right: ['left', 'top', 'bottom']
                  };

                  // are we using jQuery 1.7+
                  methods.jquery_check();

                  // can we create cookies?
                  if (!$.isFunction($.cookie)) {
                      settings.cookieMonster = false;
                  }

                  // generate the tips and insert into dom.
                  if (!settings.cookieMonster || !$.cookie(settings.cookieName)) {

                      var tipWrapper = $('<div id="' + $this[0].id + '-tip-container"></div>');
                      $(settings.tipContainer).append(tipWrapper);
                      settings.$tipWrapper = tipWrapper;
                      settings.$tip_content.each(function (index) {
                          methods.create(settings, { $li: $(this), index: index });
                      });
                      tipWrapper.css('visibility', 'hidden');

                      // show first tip
                      if (settings.autoStart) {
                          if (!settings.startTimerOnClick && settings.timer > 0) {
                              methods.show(settings,false,'init');
                              methods.startTimer(settings);
                          } else {
                              methods.show(settings,false,'init');
                          }
                      }

                  }

                  settings.$tipWrapper.on('click.joyride', '.joyride-next-tip', function (e) {
                      e.preventDefault();
                      methods.go_next(methods.getSettings(this));
                  });

                  settings.$tipWrapper.on('click.joyride', '.joyride-prev-tip', function (e) {
                      e.preventDefault();
                      methods.go_prev(methods.getSettings(this));
                  });

                  settings.$tipWrapper.on('click.joyride', '.joyride-close-tip', function (e) {
                      e.preventDefault();                      
                      $("body").trigger('closeJoyrideClick', e);
                      
                  });

                  $this.data(settings.tourDataName, settings);
              } else {
                  methods.restart(settings);
              }
          },
          

          getSettings: function (element) {
              var tourId = $(element).attr('data-tourId');
              return $(tourId).data(tourId.replace('#','') + '-tour-settings');
          },

          go_next: function (settings) {
              if (settings) {
                  if (settings.$li.next().length < 1) {
                      methods.end(settings);
                  } else if (settings.timer > 0) {
                      clearTimeout(settings.automate);
                      methods.hide(settings);
                      methods.show(settings);
                      methods.startTimer();
                  } else {
                      methods.hide(settings);
                      methods.show(settings);
                  }
              }
          },

          go_prev: function (settings) {
              if (settings && settings.$li.index() > 0) {
                  if (settings.timer > 0) {
                      clearTimeout(settings.automate);
                      methods.hide(settings);
                      methods.show(settings, 'prev');
                      methods.startTimer(settings);
                  } else {
                      methods.hide(settings);
                      methods.show(settings, 'prev');
                  }
              }
          },

          // call this method when you want to resume the tour
          resume: function (settings) {
        	  //get length of pauseAfter array
        	  var aryLen = settings.pauseAfter.length;
        	  var i;
        	  
        	  //for loop to clear pauseAfter array
        	  for(i=0; i < aryLen; i++){
        		  settings.pauseAfter.pop();
        	  }
        	  
        	  if (settings.$li.next().length < 1) {
        		  methods.set_li(settings, true);
                  methods.set_li(settings);
        	  }else{
        		  methods.set_li(settings);
                  methods.set_li(settings, true);
        	  }
        	  
              methods.show(settings);
          },

          nextTip: function (settings) {
              if (settings.$li.next().length < 1) {
                  methods.end(settings);
              } else if (settings.timer > 0) {
                  clearTimeout(settings.automate);
                  methods.hide(settings);
                  methods.show(settings);
                  methods.startTimer(settings);
              } else {
                  methods.hide(settings);
                  methods.show(settings);
              }
          },

          tip_template: function (settings, opts) {
              var $blank, content, $wrapper;

              opts.tip_class = opts.tip_class || '';

              $blank = $(settings.template.tip).addClass(opts.tip_class);
              
              content = $.trim($(opts.li).html()) +
                methods.button_prev(settings, opts.index, opts.button_prev_text) +
                methods.button_next(settings, opts.index, opts.button_next_text) +
                settings.template.link +
                methods.timer_instance(settings,opts.index);

              $wrapper = $(settings.template.wrapper);
              if (opts.li.attr('data-aria-labelledby')) {
                  $wrapper.attr('aria-labelledby', opts.li.attr('data-aria-labelledby'));
              }
              if (opts.li.attr('data-aria-describedby')) {
                  $wrapper.attr('aria-describedby', opts.li.attr('data-aria-describedby'));
              }
              $blank.append($wrapper);
              $blank.first().attr('data-index', opts.index);
              $('.joyride-content-wrapper', $blank).append(content);
              $blank.find('.joyride-close-tip').attr('data-tourId', '#' + settings.$content_el[0].id);
              return $blank[0];
          },

          timer_instance: function (settings, index) {
              var txt;

              if ((index === 0 && settings.startTimerOnClick && settings.timer > 0) || settings.timer === 0) {
                  txt = '';
              } else {
                  txt = methods.outerHTML($(settings.template.timer)[0]);
              }
              return txt;
          },
          
          set_next_delay_flag_on: function (settings){
        	  settings.next_delay_flag = true;
          },

          button_next: function (settings, index, txt) {
              if (settings.nextButton) {
                  if (index == settings.$tip_content.length - 1) {
                      txt = $.trim(txt) || 'Done';
                  } else {
                      txt = $.trim(txt) || 'Next';
                  }
                  
                  txt = methods.outerHTML($(settings.template.button).attr('data-tourId', '#' + settings.$content_el[0].id).append(txt)[0]);
              } else {
                  txt = '';
              }
              return txt;
          },

          button_prev: function (settings, index, txt) {
              if (settings.prevButton && index > 0) {
                  txt = $.trim(txt) || 'Prev';
                  txt = methods.outerHTML($(settings.template.prevbutton).attr('data-tourId', '#' + settings.$content_el[0].id).append(txt)[0]);
              } else {
                  txt = '';
              }
              return txt;
          },

          create: function (settings, opts) {
              var nextButtonText = opts.$li.attr('data-button-next'),
                  prevButtonText = opts.$li.attr('data-button-prev'),
                tipClass = opts.$li.attr('class'),
                $tipContent = $(methods.tip_template(settings,{
                    tip_class: tipClass,
                    index: opts.index,
                    button_next_text: nextButtonText,
                    button_prev_text: prevButtonText,
                    li: opts.$li
                }));

              settings.$tipWrapper.append($tipContent);
          },

          show: function (settings, prev, init) {
              var opts = {}, ii, opts_arr = [], opts_len = 0, p,
                  $timer = null;

              // are we paused?
              if (settings.$li === undefined || ($.inArray(settings.$li.index(), settings.pauseAfter) === -1)) {
                  // don't go to the next li if the tour was paused
                  if (settings.paused) {
                      settings.paused = false;
                  } else {
                      methods.set_li(settings, prev, init);
                  }

                  settings.attempts = 0;

                  if (settings.$li.length && settings.$target.length > 0) {
                      opts_arr = (settings.$li.data('options') || ':').split(';');
                      opts_len = opts_arr.length;
                      // parse options
                      for (ii = opts_len - 1; ii >= 0; ii--) {
                          p = opts_arr[ii].split(':');
                          if (p.length === 2) {
                              opts[$.trim(p[0])] = $.trim(p[1]);
                          }
                      }
                      
                      //Code to invoke preStepCallback function
                      settings.preStepCallback(settings.$li.index(), settings.$next_tip );
                      
                      settings.tipSettings = $.extend({}, settings, opts);
                      settings.tipSettings.tipLocationPattern = settings.tipLocationPatterns[settings.tipSettings.tipLocation];

                      // scroll if not modal
                      if (!/body/i.test(settings.$target.selector) && settings.scroll) {
                          methods.scroll_to(settings);
                      }
                      if (methods.is_phone(settings)) {
                          methods.pos_phone(settings, true);
                      } else {
                          methods.pos_default(settings, true);
                      }
                      
                      

                      $timer = $('.joyride-timer-indicator', settings.$next_tip);

                      if (/pop/i.test(settings.tipAnimation)) {
                          $timer.outerWidth(0);
                          if (settings.timer > 0) {
                              settings.$next_tip.show();
                              $timer.animate({
                                  width: $('.joyride-timer-indicator-wrap', settings.$next_tip).outerWidth()
                              }, settings.timer);
                          } else {
                              settings.$next_tip.show();
                          }
                      } else if (/fade/i.test(settings.tipAnimation)) {
                          $timer.outerWidth(0);
                          if (settings.timer > 0) {
                              settings.$next_tip.fadeIn(settings.tipAnimationFadeSpeed);
                              settings.$next_tip.show();
                              $timer.animate({
                                  width: $('.joyride-timer-indicator-wrap', settings.$next_tip).outerWidth()
                              }, settings.timer);
                          } else {
                              settings.$next_tip.fadeIn(settings.tipAnimationFadeSpeed);
                          }
                      }
                      

                      settings.$current_tip = settings.$next_tip;
                      // Focus next button for keyboard users.
                      //$('.joyride-next-tip', settings.$current_tip).focus();
                      methods.tabbable(settings.$current_tip);
                      methods.checkHeight(settings);

                      if (settings.mode === 'focus') {
                          settings.$target.addClass('joyride-focus-element');
                          methods.showModal(settings);
                      }
                      // skip non-existent targets
                  } else if (settings.$li && settings.$target.length < 1) {
                      methods.show(settings);
                  } else {
                      methods.end(settings);
                  }
              } else {
                  settings.paused = true;
              }
          },

          // detect phones with media queries if supported.
          is_phone: function (settings) {
              if (Modernizr) {
                  return Modernizr.mq('only screen and (max-width: 767px)');
              }

              return (settings.$window.width() < 767) ? true : false;
          },

          hide: function (settings) {
              settings.postStepCallback(settings.$li.index(), settings.$current_tip);
              if (settings.$modal_bg) {
                  settings.$modal_bg.hide();
              }
              settings.$current_tip.hide();
              if (settings.mode === 'focus') {
                  settings.$target.removeClass('joyride-focus-element');
              }
          },

          set_li: function (settings, prev, init) {
              if (init) {
                  settings.$li = settings.$tip_content.eq(settings.startOffset);
                  methods.set_next_tip(settings);
                  settings.$current_tip = settings.$next_tip;
              } else {
                  if (prev) {
                      settings.$li = settings.$li.prev();
                  } else {
                      settings.$li = settings.$li.next();
                  }
                  methods.set_next_tip(settings);
              }

              methods.set_target(settings);
          },

          set_next_tip: function (settings) {
              settings.$next_tip = settings.$tipWrapper.find('.joyride-tip-guide[data-index=' + settings.$li.index() + ']');
          },
          
          get_next_tip: function (settings) {
        	  var int_next_index = methods.get_next_index(settings);
        	  
        	  try{
        		  return settings.$tipWrapper.find('.joyride-tip-guide[data-index=' + int_next_index + ']');
        	  }catch(e){
        		  return null;
        	  }
          },
          
          get_next_index: function(settings) {
        	  var int_next = 0;
			    try{
			    	int_next = settings.$next_tip.attr('data-index');
			    }catch(e){
				    int_next = 0;
				}
        	  
        	  return int_next;
          },
          
          get_next_li: function(settings) {
        	  var num_next_index = new Number;
        	  num_next_index = methods.get_next_index(settings);
        	  
        	  //check for valid number data type 
        	  if(num_next_index >= 0){
        		  //return number
        		  return num_next_index;
        	  }else{
        		  //return -1 to indicate error
        		  return -1;
        	  }
          },

          set_target: function (settings) {
              var cl = settings.$li.attr('data-class'),
                  id = settings.$li.attr('data-id'),
                  $sel = function () {
                      if (id) {
                          return $(settings.document.getElementById(id));
                      } else if (cl) {
                          return $('.' + cl).first();
                      } else {
                          return $('body');
                      }
                  };

              settings.$target = $sel();
          },

          scroll_to: function (settings) {
              var window_half, tipOffset;

              window_half = settings.$window.height() / 2;
              tipOffset = Math.ceil(settings.$target.offset().top - window_half + settings.$next_tip.outerHeight());

              $("html, body").stop().animate({
                  scrollTop: tipOffset
              }, settings.scrollSpeed);
          },

          paused: function (settings) {
              if (($.inArray((settings.$li.index() + 1), settings.pauseAfter) === -1)) {
                  return true;
              }
              return false;
          },
          
          isPaused: function (settings){
        	  return settings.paused;
          },
          
          pause: function (settings) {
        	  settings.pauseAfter.push(settings.$li.index());
          },

          destroy: function (settings) {
              if (settings && !$.isEmptyObject(settings)) {
                  settings.$tipWrapper.off('.joyride');

                  settings.$content_el.data('tour', undefined);

                  $('.joyride-close-tip, .joyride-next-tip, .joyride-prev-tip', settings.$tipWrapper).off('.joyride');
                  settings.$tipWrapper.remove();
                  clearTimeout(settings.automate);
              }
          },

          restart: function (settings) {
              if (!settings.autoStart) {
                  if (!settings.startTimerOnClick && settings.timer > 0) {
                      methods.show(settings, false, 'init');
                      methods.startTimer(settings);
                  } else {
                      methods.show(settings, false, 'init');
                  }
                  settings.autoStart = true;
              }
              else {
                  methods.hide(settings);
                  settings.$li = undefined;
                  methods.show(settings, false, 'init');
              }
          },

          pos_default: function (settings, init) {
              var $nub = $('.joyride-nub', settings.$next_tip),
                  nub_height = Math.ceil($nub.outerHeight() / 2),
                  toggle = init || false,
                  tipOffset = $(settings.tipContainer).offset();

              // tip must not be "display: none" to calculate position
              if (toggle) {
                  settings.$next_tip.css('visibility', 'hidden');
                  settings.$next_tip.show();
              }

              if ( settings.$target && !/body/i.test(settings.$target.selector)) {

                  if (methods.bottom(settings)) {
                      var top = settings.$target.offset().top - tipOffset.top;
                      var left = settings.$target.offset().left - tipOffset.left;
                      settings.$next_tip.css({
                          top: (top + nub_height + settings.$target.outerHeight()),
                          left: left
                      });

                      methods.nub_position($nub, settings.tipSettings.nubPosition, 'top');

                  } else if (methods.top(settings)) {

                      settings.$next_tip.css({
                          top: (settings.$target.offset().top - tipOffset.top - settings.$next_tip.outerHeight() - nub_height),
                          left: settings.$target.offset().left - tipOffset.left
                      });

                      methods.nub_position($nub, settings.tipSettings.nubPosition, 'bottom');

                  } else if (methods.right(settings)) {

                      settings.$next_tip.css({
                          top: settings.$target.offset().top - tipOffset.top,
                          left: (settings.$target.outerWidth() + settings.$target.offset().left - tipOffset.left)
                      });

                      methods.nub_position($nub, settings.tipSettings.nubPosition, 'left');

                  } else if (methods.left(settings)) {

                      settings.$next_tip.css({
                          top: settings.$target.offset().top - tipOffset.top,
                          left: (settings.$target.offset().left - tipOffset.left - settings.$next_tip.outerWidth() - nub_height)
                      });

                      methods.nub_position($nub, settings.tipSettings.nubPosition, 'right');

                  }

                  if (!methods.visible(methods.corners(settings, settings.$next_tip)) && settings.attempts < settings.tipSettings.tipLocationPattern.length) {

                      $nub.removeClass('bottom')
                        .removeClass('top')
                        .removeClass('right')
                        .removeClass('left');

                      settings.tipSettings.tipLocation = settings.tipSettings.tipLocationPattern[settings.attempts];

                      settings.attempts++;

                      methods.pos_default(settings, true);

                  }

              } else if (settings.$li && settings.$li.length) {

                  methods.pos_modal(settings, $nub);

              }

              if (toggle) {
                  settings.$next_tip.hide();
                  settings.$next_tip.css('visibility', 'visible');
              }

          },

          pos_phone: function (settings, init) {
              var tip_height = settings.$next_tip.outerHeight(),
                  target_height = settings.$target.outerHeight(),
                  $nub = $('.joyride-nub', settings.$next_tip),
                  nub_height = Math.ceil($nub.outerHeight() / 2),
                  toggle = init || false;

              $nub.removeClass('bottom')
                .removeClass('top')
                .removeClass('right')
                .removeClass('left');

              if (toggle) {
                  settings.$next_tip.css('visibility', 'hidden');
                  settings.$next_tip.show();
              }

              if (!/body/i.test(settings.$target.selector)) {

                  if (methods.top(settings)) {

                      settings.$next_tip.offset({ top: settings.$target.offset().top - tip_height - nub_height });
                      $nub.addClass('bottom');

                  } else {

                      settings.$next_tip.offset({ top: settings.$target.offset().top + target_height + nub_height });
                      $nub.addClass('top');

                  }

              } else if (settings.$li.length) {

                  methods.pos_modal(settings, $nub);

              }

              if (toggle) {
                  settings.$next_tip.hide();
                  settings.$next_tip.css('visibility', 'visible');
              }
          },

          pos_modal: function (settings, $nub) {
              methods.center(settings);
              $nub.hide();

              methods.showModal(settings);
          },
          
          showModal: function (settings) {

              if (!settings.$modal_bg) {
                  settings.$modal_bg = $('<div class="joyride-modal-bg">');
                  $('body').append(settings.$modal_bg);
                  settings.$modal_bg.show();
              } else {
                  if (/pop/i.test(settings.tipAnimation)) {
                      settings.$modal_bg.show();
                  } else {
                      settings.$modal_bg.fadeIn(settings.tipAnimationFadeSpeed);
                  }
              }
          },

          center: function (settings) {
              var $w = settings.$window;

              settings.$next_tip.css({
                  top: ((($w.height() - settings.$next_tip.outerHeight()) / 2) + $w.scrollTop()),
                  left: ((($w.width() - settings.$next_tip.outerWidth()) / 2) + $w.scrollLeft())
              });

              return true;
          },

          bottom: function (settings) {
              return /bottom/i.test(settings.tipSettings.tipLocation);
          },

          top: function (settings) {
              return /top/i.test(settings.tipSettings.tipLocation);
          },

          right: function (settings) {
              return /right/i.test(settings.tipSettings.tipLocation);
          },

          left: function (settings) {
              return /left/i.test(settings.tipSettings.tipLocation);
          },

          corners: function (settings, el) {
              var w = settings.$window,
                  right = w.width() + w.scrollLeft(),
                  bottom = w.width() + w.scrollTop();

              return [
                el.offset().top <= w.scrollTop(),
                right <= el.offset().left + el.outerWidth(),
                bottom <= el.offset().top + el.outerHeight(),
                w.scrollLeft() >= el.offset().left
              ];
          },

          visible: function (hidden_corners) {
              var i = hidden_corners.length;

              while (i--) {
                  if (hidden_corners[i]) return false;
              }

              return true;
          },

          nub_position: function (nub, pos, def) {
              if (pos === 'auto') {
                  nub.addClass(def);
              } else {
                  nub.addClass(pos);
              }
          },

          startTimer: function (settings) {
              if (settings.$li.length) {
                  settings.automate = setTimeout(function () {
                      methods.hide();
                      methods.show();
                      methods.startTimer();
                  }, settings.timer);
              } else {
                  clearTimeout(settings.automate);
              }
          },

          end: function (settings) {
        	  //get length of pauseAfter array
        	  var aryLen = settings.pauseAfter.length;
        	  var i;
        	  
        	  //for loop to clear pauseAfter array
        	  for(i=0; i < aryLen; i++){
        		  settings.pauseAfter.pop();
        	  }
        	  
        	  settings.paused = false;
        	  
        	  
              if (settings.cookieMonster) {
                  $.cookie(settings.cookieName, 'ridden', { expires: 365, domain: settings.cookieDomain });
              }

              if (settings.timer > 0) {
                  clearTimeout(settings.automate);
              }

              if (settings.$modal_bg) {
                  settings.$modal_bg.hide();
              }
              settings.$current_tip.hide();
              if (settings.mode === 'focus') {
                  settings.$target.removeClass('joyride-focus-element');
              }
              methods.revertHeight(settings, $(settings.tipContainer));
              
              settings.postStepCallback(settings.$li.index(), settings.$current_tip);
              
              if (settings.$li.next().length < 1){
            	  settings.postRideCallback(settings.$li.index(), settings.$current_tip);
              }
          },

          jquery_check: function () {
              // define on() and off() for older jQuery
              if (!$.isFunction($.fn.on)) {

                  $.fn.on = function (types, sel, fn) {

                      return this.delegate(sel, types, fn);

                  };

                  $.fn.off = function (types, sel, fn) {

                      return this.undelegate(sel, types, fn);

                  };

                  return false;
              }

              return true;
          },

          outerHTML: function (el) {
              // support FireFox < 11
              return el.outerHTML || new XMLSerializer().serializeToString(el);
          },

          version: function (settings) {
              return settings.version;
          },

          tabbable: function (el) {
              $(el).on('keydown', function (event) {
                  if (!event.isDefaultPrevented() && event.keyCode &&
                      // Escape key.
                      event.keyCode === 27) {
                      event.preventDefault();
                      methods.end();
                      return;
                  }

                  // Prevent tabbing out of tour items.
                  if (event.keyCode !== 9) {
                      return;
                  }
                  var tabbables = $(el).find(":tabbable"),
                    first = tabbables.filter(":first"),
                    last = tabbables.filter(":last");
                  if (event.target === last[0] && !event.shiftKey) {
                      first.focus(1);
                      event.preventDefault();
                  } else if (event.target === first[0] && event.shiftKey) {
                      last.focus(1);
                      event.preventDefault();
                  }
              });
          },
          checkHeight: function (settings) {
              var tipContainer = $(settings.tipContainer);
              if (settings.affectHeight) {
                  if (settings.heightChanged) {
                      methods.revertHeight(settings, tipContainer);
                  }
                  var top = parseInt(settings.$current_tip.css('top'), 10);
                  var parentHeight = tipContainer.height();
                  var currentPositionHeight = top + settings.$current_tip.height();
                  var padding = settings.heightPadding;
                  if (currentPositionHeight >= (parentHeight - padding)) {
                      settings.heightChanged = true;
                      tipContainer.height(currentPositionHeight + padding);
                  } else {
                      if (settings.heightChanged) {
                          methods.revertHeight(settings,tipContainer);
                      }
                  }
              }
          },
          revertHeight: function (settings, container) {
              if (settings.affectHeight) {
                  container.css('height', '');
              }
          },
          windowResize: function (settings) {
              if (methods.is_phone(settings)) {
                  methods.pos_phone(settings);
              } else {
                  methods.pos_default(settings);
              }
          }

      };

    $.fn.joyride = function (method) {
    	var result = undefined;
    	var normalReturn = undefined;
    	
        normalReturn = this.each(function () {
            if (methods[method]) {
                var settings = $(this).data(this.id + '-tour-settings');
                var args = Array.prototype.slice.call(arguments, 1);
                args.splice(0, 0, settings);
                result = methods[method].apply(this, args);
            } else if (typeof method === 'object' || !method) {
                return methods.init(this, method);
            } else {
                return $.error('Method ' + method + ' does not exist on jQuery.joyride');
            }
        });
        if(result){
        	return result;
        }else{
        	return normalReturn;
        }
    };

    $(window).bind('resize.joyride', function () {
        $('.joyride-tour').joyride('windowResize');
    });

}(jQuery, this));
