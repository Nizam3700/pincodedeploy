const apiUrl = 'http://localhost:3700'; // Base URL for your Spring Boot API

const form = document.getElementById('pincode-form');
const resultDiv = document.getElementById('result');

form.addEventListener('submit', (e) => {
    e.preventDefault();
    const pincode = document.getElementById('pincode').value;
    if (pincode) {
        fetch(`${apiUrl}/pincode/${pincode}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to fetch pincode details');
            }
            return response.json();
        })
        .then(data => {
            if (data.length > 0) {
                const pincodeData = data[0];
                const resultHtml = `
                    <h2>Pincode Details</h2>
                    <ul>
                        <li>Area: ${pincodeData.Area}</li>
                        <li>Latitude: ${pincodeData.lat}</li>
                        <li>Longitude: ${pincodeData.lng}</li>
                        <li>District: ${pincodeData.District}</li>
                        <li>State: ${pincodeData.State}</li>
                        <li>Pincode: ${pincodeData.Pincode}</li>
                    </ul>
                `;
                resultDiv.innerHTML = resultHtml;
            } else {
                resultDiv.innerHTML = 'No details found for the entered pincode.';
            }
        })
        .catch(error => {
            console.error('Error:', error);
            resultDiv.innerHTML = 'Error: Unable to fetch pincode details';
        });
    } else {
        resultDiv.innerHTML = 'Please enter a valid pincode';
    }
});

async function fetchPincodeDetails(pincode) {
    try {
        const response = await fetch(`http://localhost:3700/pincode/${pincode}`, {
            method: 'GET',
            headers: {
                'Accept': '*/*',
                'Accept-Encoding': 'gzip, deflate, br, zstd',
                'Accept-Language': 'en-US,en;q=0.9',
                'Cache-Control': 'no-cache',
                'Connection': 'keep-alive',
                'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36'
            }
        });

        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        const data = await response.json();
        console.log(data);
        // Update your UI with the data
    } catch (error) {
        console.error('Error fetching pincode details:', error);
    }
}

fetchPincodeDetails('110001'); // Example pincode

