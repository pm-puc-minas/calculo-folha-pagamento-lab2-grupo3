function validatePassword() {
    var password = document.getElementById("formGroupExampleInput2").value;
    var confirmPassword = document.getElementById("formGroupExampleInput3").value;
    var errorDiv = document.getElementById("passwordMismatchError");

    if (password != confirmPassword) {
        errorDiv.style.display = "block";
        return false;
    } else {
        errorDiv.style.display = "none";
        return true;
    }
}
