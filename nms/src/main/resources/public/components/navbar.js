export default {
    template: `
        <nav class="navbar navbar-expand-lg navbar-dark" style="background-color: black;">
            <!-- Logo on the left -->
            <div class="container-fluid">
                <a class="navbar-brand d-flex align-items-center" href="#" style="font-family: 'Ericsson Hilda', sans-serif; font-weight: 700;">
                    <img src="assets/images/ECON_RGB_48px.png" alt="Logo" class="me-2" style="width: 40px; height: 40px;">
                    <span>Network Manager</span>
                </a>

                <!-- Toggler for small screens -->
                <button 
                    class="navbar-toggler" 
                    type="button" 
                    data-bs-toggle="collapse" 
                    data-bs-target="#navbarNav" 
                    aria-controls="navbarNav" 
                    aria-expanded="false" 
                    aria-label="Toggle navigation"
                >
                    <span class="navbar-toggler-icon"></span>
                </button>

                <!-- Navigation links on the right -->
                <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
                    <ul class="navbar-nav" style="font-family: 'Ericsson Hilda', sans-serif; font-weight: 400;">
                        <li class="nav-item">
                            <a class="nav-link text-white" href="#">Profile</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link text-white" href="#">Logout</a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
    `
};