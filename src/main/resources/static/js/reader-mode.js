document.getElementById('reader-mode-btn').addEventListener('click', function () {
    if(localStorage.getItem('reader-mode-enabled') === '/css/reader-mode.css'){
        document.getElementById('mode_css').href = '';
        localStorage.setItem('reader-mode-enabled', '');
    }
    else {
        $('div.panel-collapse').addClass('in');
        document.getElementById('mode_css').href = '/css/reader-mode.css';
        localStorage.setItem('reader-mode-enabled', '/css/reader-mode.css');
    }
});

if (localStorage.getItem('reader-mode-enabled') === '/css/reader-mode.css') {
    $('div.panel-collapse').addClass('in');
    document.getElementById('mode_css').href = '/css/reader-mode.css';
}
