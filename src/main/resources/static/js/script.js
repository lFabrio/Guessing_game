function combineInputs() {
    let digitOne = document.getElementById('digitOne').value;
    let digitTwo = document.getElementById('digitTwo').value;
    let digitThree = document.getElementById('digitThree').value;
    let digitFour = document.getElementById('digitFour').value;

    let combinedValue = digitOne + digitTwo + digitThree + digitFour;
    document.getElementById('combinedGuess').value = combinedValue;
}
