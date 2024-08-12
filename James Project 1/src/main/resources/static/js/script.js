document.addEventListener("DOMContentLoaded", function() {
    // fetch campaign data
    fetch('/api/1.0/marketing/campaigns')
        .then(response => response.json())
        .then(data => {
            const heroSection = document.getElementById('hero');
            const heroText = document.getElementById('hero-text');
            const dotsContainer = document.getElementById('dots');
            const campaigns = data.data;

            let currentIndex = 0;

            function displayCampaign(index) {
                const campaign = campaigns[index];
                const picture = campaign.picture;
                const story = campaign.story;

                heroSection.style.backgroundImage = `url(${picture})`;
                heroText.innerHTML = story.split('\n').join('<br>');

                // update color of dots
                const dots = dotsContainer.children;
                for (let i = 0; i < dots.length; i++) {
                    dots[i].classList.remove('active');
                }
                dots[index].classList.add('active');
            }

            // Create dots
            campaigns.forEach((campaign, index) => {
                const dot = document.createElement('span');
                dot.classList.add('dot');
                if (index === 0) {
                    dot.classList.add('active');
                }
                dot.addEventListener('click', (event) => {
                    event.stopPropagation(); // Prevent event from propagating to heroSection
                    currentIndex = index; // Update currentIndex when dot is clicked
                    displayCampaign(index);
                });
                dotsContainer.appendChild(dot);
            });

            // Display first campaign
            displayCampaign(currentIndex);

            // Add click event to hero section to jump to the product page
            heroSection.addEventListener('click', function(event) {
                // Check if the clicked element is a dot
                if (!event.target.classList.contains('dot')) {
                    const campaign = campaigns[currentIndex];
                    window.location.href = `product.html?id=${campaign.product_id}`;
                }
            });
        })
        .catch(error => console.error('Error fetching campaigns:', error));

    // Listener for header
    document.querySelectorAll('nav ul li a').forEach(link => {
        link.addEventListener('click', function(event) {
            event.preventDefault();
            const category = this.getAttribute('data-category');
            // renew url but not refresh the whole page
            const newUrl = `index.html?category=${category}`;
            history.pushState({ category }, '', newUrl);
            fetchProducts(category);
        });
    });

    // For going back to last page
    window.addEventListener('popstate', function(event) {
        if (event.state && event.state.category) {
            fetchProducts(event.state.category);
        } else {
            fetchProducts('all');
        }
    });

    // Load products according to category
    const urlParams = new URLSearchParams(window.location.search);
    const initialCategory = urlParams.get('category') || 'all';
    fetchProducts(initialCategory);

    function fetchProducts(category) {
        fetch(`/api/1.0/products/${category}`)
            .then(response => response.json())
            .then(data => {
                const productsSection = document.getElementById('products');
                const products = data.data;

                // Clean up
                productsSection.innerHTML = '';

                // Load products
                products.forEach(product => {
                    const productDiv = document.createElement('div');
                    productDiv.classList.add('product');

                    const colors = product.colors.map(color => `
                        <span class="color-dot" style="background-color: #${color.code};" title="${color.name}"></span>
                    `).join('');

                    productDiv.innerHTML = `
                        <a href="product.html?id=${product.id}">
                            <img src="${product.main_image}" alt="${product.title}">
                        </a>
                        <div class="colors">${colors}</div>
                        <h2>${product.title}</h2>
                        <p>TWD.${product.price}</p>
                    `;

                    productsSection.appendChild(productDiv);
                });
            })
            .catch(error => console.error('Error fetching products:', error));
    }
});
