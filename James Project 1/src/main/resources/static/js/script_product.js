document.addEventListener("DOMContentLoaded", function() {
    const productId = new URLSearchParams(window.location.search).get('id'); // Get id from url

    if (productId) {
        fetch(`/api/1.0/products/details?id=${productId}`)
            .then(response => response.json())
            .then(data => {
                const product = data.data;

                const productDetails = document.getElementById('product-details');
                productDetails.innerHTML = `
                    <div class="product-info-container">
                        <img src="${product.main_image}" alt="${product.title}" class="product-main-image">
                        <div class="product-info">
                            <h1>${product.title}</h1>
                            <div class="id">${product.id}</div>
                            <div class="price">TWD.${product.price}</div>
                            <div class="description">${product.description}</div>
                            <div class="details">
                            <div class="detail-row">
                                顏色 |
                                <div class="colors">
                                    ${product.colors.map(color => `
                                        <button class="color-dot" style="background-color: #${color.code};" data-color="${color.code}"></button>
                                    `).join('')}
                                </div>
                            </div>
                            <div class="detail-row">
                                尺寸 |
                                <div class="sizes">
                                    ${['S', 'M', 'L'].map(size => {
                                        const variant = product.variants.find(v => v.size === size);
                                        const inStock = variant ? variant.stock > 0 : false;
                                        return `
                                            <button class="size-button ${inStock ? '' : 'out-of-stock'}" data-size="${size}" ${inStock ? '' : 'disabled'}>${size}</button>
                                        `;
                                    }).join('')}
                                </div>
                            </div>
                            <div class="detail-row">
                                數量 |
                                <div class="quantity-selector">
                                    <button class="quantity-decrease">-</button>
                                    <input type="number" value="1" min="1" class="quantity-input">
                                    <button class="quantity-increase">+</button>
                                </div>
                            </div>
                            <div>
                                    <button class="add-to-cart">加入購物車</button>
                            </div>
                            <div class="additional-details">
                                <div>備註：${product.note}</div>                                
                                <div>材質：${product.texture}</div>
                                <div>清洗方式：${product.wash}</div>
                                <div>產地：${product.place}</div>
                            </div>
                            </div>
                        </div>
                    </div>
                    <div class="more-info">
                        <h2>更多產品資訊</h2>
                        <p>${product.story}</p>
                        ${product.images.map(image => `
                            <img src="${image}" alt="More info" class="more-info-image">
                        `).join('')}
                    </div>
                `;

                let selectedColor = null;
                let selectedSize = null;
                let maxQuantity = 1;

                // EventListeners
                document.querySelectorAll('.color-dot').forEach(button => {
                    button.addEventListener('click', function() {
                        document.querySelectorAll('.color-dot').forEach(btn => btn.classList.remove('selected'));
                        this.classList.add('selected');
                        selectedColor = this.getAttribute('data-color');
                        updateSizeButtons();
                        updateMaxQuantity();
                    });
                });

                document.querySelectorAll('.size-button').forEach(button => {
                    button.addEventListener('click', function() {
                        document.querySelectorAll('.size-button').forEach(btn => btn.classList.remove('selected'));
                        this.classList.add('selected');
                        selectedSize = this.getAttribute('data-size');
                        updateMaxQuantity();
                    });
                });

                function updateSizeButtons() {
                    document.querySelectorAll('.size-button').forEach(button => {
                        const size = button.getAttribute('data-size');
                        const variant = product.variants.find(v => v.size === size && v.color_code === selectedColor);
                        const inStock = variant ? variant.stock > 0 : false;
                        if (inStock) {
                            button.classList.remove('out-of-stock');
                            button.disabled = false;
                        } else {
                            button.classList.add('out-of-stock');
                            button.disabled = true;
                        }
                    });
                }

                function updateMaxQuantity() {
                    if (selectedColor && selectedSize) {
                        const variant = product.variants.find(v => v.size === selectedSize && v.color_code === selectedColor);
                        maxQuantity = variant ? variant.stock : 1;
                        const quantityInput = document.querySelector('.quantity-input');
                        quantityInput.max = maxQuantity;
                        if (parseInt(quantityInput.value) > maxQuantity) {
                            quantityInput.value = maxQuantity;
                        }
                    }
                }

                document.querySelector('.quantity-decrease').addEventListener('click', function() {
                    const input = document.querySelector('.quantity-input');
                    const currentValue = parseInt(input.value);
                    if (currentValue > 1) {
                        input.value = currentValue - 1;
                    }
                });

                document.querySelector('.quantity-increase').addEventListener('click', function() {
                    const input = document.querySelector('.quantity-input');
                    const currentValue = parseInt(input.value);
                    if (currentValue < maxQuantity) {
                        input.value = currentValue + 1;
                    }
                });

                // Event listener for "加入購物車" button
                document.querySelector('.add-to-cart').addEventListener('click', function() {
                    if (!isUserLoggedIn()) {
                        window.location.href = `profile.html`;
                        return;
                    }
                    if (selectedColor && selectedSize) {
                        document.getElementById('payment-modal').style.display = 'block';
                    } else {
                        alert('請選擇顏色和尺寸');
                    }
                });

                // Event listener for modal close button
                document.querySelector('.close-button').addEventListener('click', function() {
                    document.getElementById('payment-modal').style.display = 'none';
                });

                // Initialize TapPay Fields
                TPDirect.setupSDK(12348, 'app_pa1pQcKoY22IlnSXq5m5WP5jFKzoRG58VEXpT7wU62ud7mMbDOGzCYIlzzLF', 'sandbox');
                TPDirect.card.setup({
                    fields: {
                        number: {
                            element: '#card-number',
                            placeholder: '**** **** **** ****'
                        },
                        expirationDate: {
                            element: '#card-expiration-date',
                            placeholder: 'MM / YY'
                        },
                        ccv: {
                            element: '#card-ccv',
                            placeholder: '後三碼'
                        }
                    },
                    styles: {
                        'input': {
                            'color': 'gray',
                            'font-size': '16px',
                            'line-height': '24px',
                            'padding': '10px',
                            'margin-bottom': '5px',
                            'border': '1px solid #ced4da',
                            'border-radius': '4px',
                            'background-color': '#fff',
                            'box-shadow': '0 1px 3px rgba(0, 0, 0, 0.1)',
                            'display': 'block',
                            'width': '100%',
                            'box-sizing': 'border-box'
                        },
                        'input.ccv': {
                            'font-size': '16px'
                        },
                        ':focus': {
                            'color': 'black',
                            'border-color': '#80bdff',
                            'outline': '0',
                            'box-shadow': '0 0 0 0.2rem rgba(0, 123, 255, 0.25)'
                        },
                        '.valid': {
                            'color': 'green'
                        },
                        '.invalid': {
                            'color': 'red'
                        },
                        '@media screen and (max-width: 400px)': {
                            'input': {
                                'font-size': '14px'
                            }
                        }
                    },
                    isMaskCreditCardNumber: true,
                    maskCreditCardNumberRange: {
                        beginIndex: 6,
                        endIndex: 11
                    }
                });

                document.getElementById('pay-button').addEventListener('click', function() {
                    TPDirect.card.getPrime(function(result) {
                        if (result.status !== 0) {
                            alert('Failed to get prime');
                            return;
                        }
                        const prime = result.card.prime;
                        handlePayment(prime);
                    });
                });

                function isUserLoggedIn() {
                    const accessToken = localStorage.getItem('accessToken');
                    return !!accessToken;
                }

                function handlePayment(prime) {
                    const accessToken = localStorage.getItem('accessToken');

                    // Gather order details
                    const orderDetails = {
                        prime: prime,
                        shipping: 'delivery',
                        payment: 'credit_card',
                        subtotal: product.price,
                        freight: 60,
                        total: product.price + 60,
                        recipient: {
                            name: 'User Name',
                            phone: '0912345678',
                            email: 'user@example.com',
                            address: 'User Address',
                            time: 'morning'
                        },
                        list: [
                            {
                                id: product.id,
                                name: product.title,
                                price: product.price,
                                colors: [{ code: selectedColor, name: selectedColor }],
                                size: selectedSize,
                                qty: parseInt(document.querySelector('.quantity-input').value)
                            }
                        ]
                    };

                    console.log('Order data to be sent:', orderDetails);  // Debugging line

                    fetch('/api/1.0/order/checkout', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json',
                            'Authorization': `Bearer ${accessToken}`
                        },
                        body: JSON.stringify(orderDetails)
                    })
                    .then(response => {
                        if (!response.ok) {
                            return response.json().then(data => {
                                throw new Error(data.message || 'Unknown error');
                            }).catch(err => {
                                throw new Error(response.statusText || 'Unknown error');
                            });
                        }
                        return response.json();
                    })
                    .then(data => {
                        console.log('Response data:', data); // Debugging line
                        if ("data" in data) {
                            window.location.href = 'thankyou.html';
                        } else {
                            alert('Error: ' + data.message);
                        }
                    })
                    .catch(error => {
                        console.error('Error processing payment:', error);
                        alert('Error: ' + error.message);
                    });
                }
            })
            .catch(error => console.error('Error fetching product details:', error));
    } else {
        document.getElementById('product-details').innerHTML = '<p>此產品不存在</p>';
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
