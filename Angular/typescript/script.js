"use strict";
const fetchBtn = document.getElementById('fetchBtn');
const dogImage = document.getElementById('dogImage');
if (fetchBtn && dogImage) {
    async function getDogImage() {
        fetchBtn.disabled = true;
        fetchBtn.textContent = "Loading...";
        try {
            const response = await fetch('https://dog.ceo/api/breeds/image/random');
            if (!response.ok) {
                throw new Error(`HTTP error! ${response.status}`);
            }
            const data = await response.json();
            dogImage.src = data.message;
            dogImage.alt = `Random doggo`;
        }
        catch (error) {
            console.error("Error:", error);
            alert("Error while loading cute doggo image!");
        }
        finally {
            fetchBtn.disabled = false;
            fetchBtn.textContent = "Show me a dog!";
        }
    }
    fetchBtn.addEventListener('click', () => {
        getDogImage();
    });
}
else {
    console.error("Error dom");
}
