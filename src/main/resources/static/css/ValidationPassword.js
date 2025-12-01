function validatePassword() {
    var password = document.getElementById("inputPassword").value;
    var confirmPassword = document.getElementById("inputConfirmPassword").value;
    var errorDiv = document.getElementById("passwordMismatchError");

    if (password != confirmPassword) {
        errorDiv.style.display = "block";
        return false;
    } else {
        errorDiv.style.display = "none";
        return true;
    }
}
