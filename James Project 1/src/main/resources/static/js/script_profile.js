document.addEventListener("DOMContentLoaded", function() {
    const mainContent = document.getElementById('main-content');
    const accessToken = localStorage.getItem('accessToken');

    if (accessToken) {
        //  User is logged in
        fetchUserProfile(accessToken);
    } else {
        //  User not logged in
        alert('尚未登入');
        showAuthForms();
    }

    function fetchUserProfile(token) {
        fetch('/api/1.0/user/profile', {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        })
        .then(response => response.json())
        .then(data => {
            if (data.error) {
                showAuthForms();
            } else {
                updateToWelcomePage(data.data);
            }
        })
        .catch(error => {
            console.error('Error fetching user profile:', error);
            showAuthForms();
        });
    }

    function showAuthForms() {
        mainContent.innerHTML = `
            <div id="auth-forms">
                <div id="signup-form">
                    <h2>註冊</h2>
                    <input type="text" id="signup-name" placeholder="Name">
                    <input type="email" id="signup-email" placeholder="Email">
                    <input type="password" id="signup-password" placeholder="Password">
                    <button id="signup-button">註冊</button>
                </div>
                <div id="signin-form">
                    <h2>登入</h2>
                    <input type="email" id="signin-email" placeholder="Email">
                    <input type="password" id="signin-password" placeholder="Password">
                    <button id="signin-button">登入</button>
                </div>
            </div>
        `;

        document.getElementById('signup-button').addEventListener('click', function() {
            const name = document.getElementById('signup-name').value;
            const email = document.getElementById('signup-email').value;
            const password = document.getElementById('signup-password').value;

            if (!validateEmail(email)) {
                alert('請輸入有效的電子郵件地址');
                return;
            }

            signUp(name, email, password);
        });

        document.getElementById('signin-button').addEventListener('click', function() {
            const email = document.getElementById('signin-email').value;
            const password = document.getElementById('signin-password').value;

            if (!validateEmail(email)) {
                alert('請輸入有效的電子郵件地址');
                return;
            }

            signIn(email, password);
        });
    }

    function signUp(name, email, password) {
        fetch('/api/1.0/user/signup', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ name, email, password })
        })
        .then(response => response.json())
        .then(data => {
            if (data.data && data.data.access_token) {
                localStorage.setItem('accessToken', data.data.access_token);
                updateToWelcomePage(data.data.user);
            } else {
                alert('註冊失敗');
            }
        })
        .catch(error => console.error('Error:', error));
    }

    function signIn(email, password) {
        fetch('/api/1.0/user/signin', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ email, password, provider: 'native' })
        })
        .then(response => response.json())
        .then(data => {
            if (data.data && data.data.access_token) {
                localStorage.setItem('accessToken', data.data.access_token);
                updateToWelcomePage(data.data.user);
            } else {
                alert('登入失敗');
            }
        })
        .catch(error => console.error('Error:', error));
    }

    function updateToWelcomePage(user) {
        const mainContent = document.getElementById('main-content');
        mainContent.innerHTML = `
            <div class="welcome-message">
                <h1>歡迎！${user.name}</h1>
                <button id="logout">登出</button>
            </div>
        `;

        document.getElementById('logout').addEventListener('click', function() {
            localStorage.removeItem('accessToken');
            location.reload();
        });
    }


    function validateEmail(email) {
        const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return re.test(email);
    }

    // Listener for header
    document.querySelectorAll('nav ul li a').forEach(link => {
        link.addEventListener('click', function(event) {
            event.preventDefault();
            const category = this.getAttribute('data-category');
            window.location.href = `index.html?category=${category}`;
        });
    });

});
