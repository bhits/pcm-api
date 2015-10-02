$(document).ready(function(){
	// 1. This code loads the IFrame Player API code asynchronously.
	var tag = document.createElement('script');
	tag.src = "https://www.youtube.com/player_api";
	var firstScriptTag = document.getElementsByTagName('script')[0];
	firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);
	var player;
	var player2;
	
	//issue #473 fix start
	$(".youtube_Transcript_one").hide();
	$(".youtube_Transcript_button_one").click(function(){
		$(".youtube_Transcript_one").toggle();	
	});
	
	$(".youtube_Transcript_two").hide();
	$(".youtube_Transcript_button_two").click(function(){
		$(".youtube_Transcript_two").toggle();	
	});
	
	//issue #473 fix end
});

//2. This function creates an <iframe> (and YouTube player)
// after the API code downloads.
function onYouTubePlayerAPIReady() {
	player = new YT.Player('player', {
		height : '315',
		width : '100%',
		videoId : 'M7ttn21jVfI',
		
		//issue #522 fix start
		//There is an 'onReady' event that is triggered by the player,the onPlayerReadyOne and/or onPlayerReadayTwo function will execute when the onReady event fires
		//the two functions indicate that it should begin to play when the video player is ready and it should pause the video when the modal is hidden
		
		events: {
            'onReady': onPlayerReadyOne
		}
	});
	
	player2 = new YT.Player('player2', {
		height : '315',
		width : '100%',
		videoId : 'FKTHncn-5Vs',
		events: {
            'onReady': onPlayerReadyTwo
		}
	});
}

function onPlayerReadyOne(event) {
	//Pause the video when modal is hidden
	$('#what-is-a-consent-video').on('hidden.bs.modal', function() {
		player.pauseVideo();
	});
	
	//Play the video when model is shown
	$('#what-is-a-consent-video').on('show.bs.modal', function() {
		player.playVideo();
	});          
}

function onPlayerReadyTwo(event) {
	//Pause the video when modal is hidden
	$('#why-is-privacy-important').on('hidden.bs.modal', function() {
		player2.pauseVideo();
	});
	
	//Play the video when model is shown
	$('#why-is-privacy-important').on('show.bs.modal', function() {
		player2.playVideo();
	});	
}	
//issue #522 fix end