export default {
    template: `
        <div class="container mt-4">
            <div class="row">
                <div class="col-md-6 offset-md-3">
                    <div class="card shadow-lg">
                        <div class="card-header bg-primary text-white">
                            <h5>Main 1 Component</h5>
                        </div>
                        <div class="card-body">
                            <p class="card-text">This is an example of a Bootstrap-styled component inside Vue.</p>
                            <button class="btn btn-success" @click="showAlert">Click Me</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    `,
    methods: {
        showAlert() {
            alert("Bootstrap + Vue is working!");
        }
    }
};
