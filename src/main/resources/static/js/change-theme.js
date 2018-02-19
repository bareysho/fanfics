document.getElementById('change-theme-btn').addEventListener('click', function () {
    if(localStorage.getItem('dark-theme-enabled') === '/css/black.css'){
        document.getElementById('theme_css').href = '';
        localStorage.setItem('dark-theme-enabled', '');
    }
    else {
        document.getElementById('theme_css').href = '/css/black.css';
        localStorage.setItem('dark-theme-enabled', '/css/black.css');
    }
});

if (localStorage.getItem('dark-theme-enabled') === '/css/black.css') {
    document.getElementById('theme_css').href = '/css/black.css';
}

