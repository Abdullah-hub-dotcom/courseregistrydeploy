const BASE_URL = "http://localhost:8080";

const loginPanel = document.getElementById("loginPanel");
const loginForm = document.getElementById("loginForm");
const loginMessage = document.getElementById("loginMessage");

const signupPanel = document.getElementById("signupPanel");
const signupForm = document.getElementById("signupForm");
const signupMessage = document.getElementById("signupMessage");

const userPanel = document.getElementById("userPanel");
const adminPanel = document.getElementById("adminPanel");

const userCoursesTable = document.querySelector("#coursesTable tbody");
const courseSelect = document.getElementById("courseSelect");
const adminCoursesTable = document.querySelector("#adminCoursesTable tbody");
const enrolledTable = document.querySelector("#enrolledTable tbody");

let authHeader = "";
let isAdmin = false; // track role

// ---------------- Fade Functions ----------------
function fadeOut(element) {
    element.classList.remove("show");
    setTimeout(() => element.style.display = "none", 600);
}

function fadeIn(element) {
    element.style.display = "block";
    setTimeout(() => element.classList.add("show"), 10);
}

// ---------------- Login ----------------
loginForm.addEventListener("submit", async (e) => {
    e.preventDefault();
    const username = document.getElementById("loginUsername").value;
    const password = document.getElementById("loginPassword").value;

    authHeader = "Basic " + btoa(username + ":" + password);

    // Determine role by trying admin endpoint first
    const adminRes = await fetch(`${BASE_URL}/admin/enrolled`, {
        headers: { "Authorization": authHeader }
    });

    if (adminRes.status === 200) {
        isAdmin = true;
        fadeOut(loginPanel);
        fadeOut(signupPanel);
        fadeIn(adminPanel);
        fetchCourses();
        fetchEnrolled();
    } else {
        const userRes = await fetch(`${BASE_URL}/user/courses`, {
            headers: { "Authorization": authHeader }
        });
        if (userRes.status === 200) {
            isAdmin = false;
            fadeOut(loginPanel);
            fadeOut(signupPanel);
            fadeIn(userPanel);
            fetchCourses();
        } else {
            loginMessage.textContent = "❌ Login failed! Please check credentials.";
        }
    }
});

// ---------------- Signup ----------------
signupForm.addEventListener("submit", async (e) => {
    e.preventDefault();
    const username = document.getElementById("signupUsername").value;
    const password = document.getElementById("signupPassword").value;
    const role = document.getElementById("signupRole").value;

    try {
        const params = new URLSearchParams({ username, password, role });
        const res = await fetch(`${BASE_URL}/auth/signup?${params.toString()}`, { method: "POST" });
        const text = await res.text();
        signupMessage.textContent = text;

        if (res.ok) {
            signupForm.reset();
            alert("✅ Signup successful! You can now login.");
            fadeOut(signupPanel);
            fadeIn(loginPanel);
        }
    } catch (err) {
        signupMessage.textContent = "❌ Signup failed! Please try again.";
        console.error(err);
    }
});

// ---------------- Fetch Courses ----------------
async function fetchCourses() {
    const endpoint = isAdmin ? `${BASE_URL}/admin/courses` : `${BASE_URL}/user/courses`;

    const res = await fetch(endpoint, {
        headers: { "Authorization": authHeader }
    });
    const courses = await res.json();

    userCoursesTable.innerHTML = "";
    courseSelect.innerHTML = `<option value="">Select Course</option>`;
    adminCoursesTable.innerHTML = "";

    courses.forEach(course => {
        if (!isAdmin) {
            const tr = document.createElement("tr");
            tr.innerHTML = `
                <td>${course.courseId}</td>
                <td>${course.courseName}</td>
                <td>${course.trainer}</td>
                <td>${course.durationInWeeks}</td>
                <td>--</td>
            `;
            userCoursesTable.appendChild(tr);

            const option = document.createElement("option");
            option.value = course.courseName;
            option.textContent = course.courseName;
            courseSelect.appendChild(option);
        }

        if (isAdmin) {
            const atr = document.createElement("tr");
            atr.innerHTML = `
                <td>${course.courseId}</td>
                <td>${course.courseName}</td>
                <td>${course.trainer}</td>
                <td>${course.durationInWeeks}</td>
                <td><button onclick="deleteCourse('${course.courseId}')" class="btn btn-danger btn-sm">Delete</button></td>
            `;
            adminCoursesTable.appendChild(atr);
        }
    });
}

// ---------------- Fetch Enrolled Students ----------------
async function fetchEnrolled() {
    if (!isAdmin) return;

    const res = await fetch(`${BASE_URL}/admin/enrolled`, {
        headers: { "Authorization": authHeader }
    });
    const enrolled = await res.json();
    enrolledTable.innerHTML = "";
    enrolled.forEach(student => {
        const tr = document.createElement("tr");
        tr.innerHTML = `
            <td>${student.id}</td>
            <td>${student.name}</td>
            <td>${student.emailid}</td>
            <td>${student.courseName}</td>
        `;
        enrolledTable.appendChild(tr);
    });
}

// ---------------- Add/Delete Course ----------------
const addCourseForm = document.getElementById("addCourseForm");
addCourseForm.addEventListener("submit", async (e) => {
    e.preventDefault();

    const courseId = document.getElementById("courseId").value;
    const courseName = document.getElementById("courseName").value;
    const trainer = document.getElementById("trainer").value;
    const durationInWeeks = parseInt(document.getElementById("duration").value);

    const response = await fetch(`${BASE_URL}/admin/addcourse`, {
        method: "POST",
        headers: {
            "Authorization": authHeader,
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ courseId, courseName, trainer, durationInWeeks })
    });

    if (response.ok) {
        alert("✅ Course added successfully!");
        addCourseForm.reset();
        fetchCourses();
    } else {
        alert("❌ Failed to add course. Check server logs.");
    }
});

async function deleteCourse(courseId) {
    if (!confirm(`Are you sure you want to delete course ID: ${courseId}?`)) return;

    const response = await fetch(`${BASE_URL}/admin/deletecourse/${courseId}`, {
        method: "DELETE",
        headers: { "Authorization": authHeader }
    });

    if (response.ok) {
        alert("✅ Course deleted successfully!");
        fetchCourses();
    } else {
        alert("❌ Failed to delete course. Check server logs.");
    }
}
// ---------------- Panel Toggle ----------------
const showSignupLink = document.getElementById("showSignup");
const showLoginLink = document.getElementById("showLogin");

showSignupLink.addEventListener("click", (e) => {
    e.preventDefault();
    fadeOut(loginPanel);
    fadeIn(signupPanel);
    loginMessage.textContent = "";
});

showLoginLink.addEventListener("click", (e) => {
    e.preventDefault();
    fadeOut(signupPanel);
    fadeIn(loginPanel);
    signupMessage.textContent = "";
});
// ---------------- Register Course ----------------
const registerForm = document.getElementById("registerForm");
const registerMessage = document.getElementById("registerMessage");

registerForm.addEventListener("submit", async (e) => {
    e.preventDefault();

    const name = document.getElementById("name").value;
    const emailid = document.getElementById("emailid").value;
    const courseName = document.getElementById("courseSelect").value;

    if (!courseName) {
        registerMessage.textContent = "❌ Please select a course!";
        return;
    }

    try {
        const params = new URLSearchParams({ name, emailid, courseName });
        const res = await fetch(`${BASE_URL}/user/registercourse?${params.toString()}`, {
            method: "POST",
            headers: { "Authorization": authHeader }
        });

        const text = await res.text();
        registerMessage.textContent = text;

        if (res.ok) {
            registerForm.reset();
            alert("✅ You have successfully registered for " + courseName);
        }
    } catch (err) {
        registerMessage.textContent = "❌ Registration failed! Please try again.";
        console.error(err);
    }
});
