var ADMIN1 = "@admin.ru";
var ADMIN2 = "@superadmin.ru";
var TEACHER = "@teacher.ru";
function getRole(email) {
  var e = email.trim().toLowerCase();
  if (e.endsWith(ADMIN1) || e.endsWith(ADMIN2)) {
    return "admin";
  }
  if (e.endsWith(TEACHER)) {
    return "teacher";
  }
  return "";
}
var students = [];
var loginScreen   = document.getElementById("loginScreen");
var adminScreen   = document.getElementById("adminScreen");
var teacherScreen = document.getElementById("teacherScreen");
function showScreen(screen) {
  loginScreen.classList.remove("active");
  adminScreen.classList.remove("active");
  teacherScreen.classList.remove("active");
  screen.classList.add("active");
}
var loginBtn   = document.getElementById("loginBtn");
var emailInput = document.getElementById("emailInput");
var loginError = document.getElementById("loginError");

loginBtn.onclick = function () {
  var email = emailInput.value.trim();
  loginError.classList.add("hidden");
  if (email === "") {
    loginError.textContent = "Введите почту";
    loginError.classList.remove("hidden");
    return;
  }
  var role = getRole(email);
  if (role === "admin") {
    document.getElementById("adminNameInput").value = "";
    document.getElementById("adminSearchInput").value = "";
    document.getElementById("adminSearchResult").textContent = "";
    renderAdminList();
    showScreen(adminScreen);
  } else if (role === "teacher") {
    document.getElementById("searchInput").value = "";
    document.getElementById("searchResult").textContent = "";
    showScreen(teacherScreen);
  } else {
    loginError.textContent = "Ошибка";
    loginError.classList.remove("hidden");
  }
};

document.getElementById("addStudentBtn").onclick = function () {
  var input = document.getElementById("adminNameInput");
  var name = input.value.trim();
  if (name === "") return;
  students.push(name);
  input.value = "";
  renderAdminList();
};

document.getElementById("deleteStudentBtn").onclick = function () {
  var input = document.getElementById("adminNameInput");
  var name = input.value.trim();
  if (name === "") return;
  var newList = [];
  for (var i = 0; i < students.length; i++) {
    if (students[i].toLowerCase() !== name.toLowerCase()) {
      newList.push(students[i]);
    }
  }
  students = newList;
  input.value = "";
  renderAdminList();
};

function renderAdminList() {
  var box = document.getElementById("adminAllStudents");
  if (students.length === 0) {
    box.textContent = "";
    return;
  }
  var text = "";
  for (var i = 0; i < students.length; i++) {
    text += students[i] + "\n";
  }
  box.textContent = text;
}

document.getElementById("adminSearchBtn").onclick = function () {
  var q = document.getElementById("adminSearchInput").value.trim().toLowerCase();
  var box = document.getElementById("adminSearchResult");
  if (q === "" || students.length === 0) {
    box.textContent = "";
    return;
  }
  var text = "";
  for (var i = 0; i < students.length; i++) {
    if (students[i].toLowerCase().indexOf(q) !== -1) {
      text += students[i] + "\n";
    }
  }
  box.textContent = text;
};

document.getElementById("searchBtn").onclick = function () {
  var q = document.getElementById("searchInput").value.trim().toLowerCase();
  var box = document.getElementById("searchResult");
  if (q === "" || students.length === 0) {
    box.textContent = "";
    return;
  }
  var text = "";
  for (var i = 0; i < students.length; i++) {
    if (students[i].toLowerCase().indexOf(q) !== -1) {
      text += students[i] + "\n";
    }
  }
  box.textContent = text;
};