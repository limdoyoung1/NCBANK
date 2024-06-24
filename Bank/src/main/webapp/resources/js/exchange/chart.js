document.addEventListener("DOMContentLoaded", function() {
    const labels = Object.keys(exchangeRates.standard);
    const standardRates = labels.map(code => exchangeRates.standard[code]);
    const buyRates = labels.map(code => exchangeRates.buy[code]);
    const sellRates = labels.map(code => exchangeRates.sell[code]);

    const ctx = document.getElementById('exchangeRateChart').getContext('2d');
    const exchangeRateChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [
                {
                    label: 'Standard Rate',
                    data: standardRates,
                    backgroundColor: 'rgba(75, 192, 192, 0.2)',
                    borderColor: 'rgba(75, 192, 192, 1)',
                    borderWidth: 1
                },
                {
                    label: 'Buy Rate',
                    data: buyRates,
                    backgroundColor: 'rgba(153, 102, 255, 0.2)',
                    borderColor: 'rgba(153, 102, 255, 1)',
                    borderWidth: 1
                },
                {
                    label: 'Sell Rate',
                    data: sellRates,
                    backgroundColor: 'rgba(255, 159, 64, 0.2)',
                    borderColor: 'rgba(255, 159, 64, 1)',
                    borderWidth: 1
                }
            ]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });
});
