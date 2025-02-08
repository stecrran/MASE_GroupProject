export default {
    template: `
        <div class="container mt-5 p-4 rounded shadow bg-light text-center">
            <h2 class="mb-4 text-primary">File Upload System</h2>
            <p class="text-muted">Click the button below to upload the sample file.</p>
            
            <div class="form-group mt-3">
                <button class="btn btn-lg btn-primary" @click="uploadData" :disabled="isUploading">
                    {{ isUploading ? "Uploading..." : "Upload Sample File" }}
                </button>
            </div>

            <div class="progress mt-3" v-if="isUploading">
                <div class="progress-bar progress-bar-striped progress-bar-animated bg-success" 
                     role="progressbar" 
                     :style="{ width: progress + '%' }">
                    {{ progress }}%
                </div>
            </div>

            <div v-if="alertMessage" :class="['alert', alertClass, 'mt-3']" role="alert">
                {{ alertMessage }}
            </div>
        </div>
    `,

    data() {
        return {
            fileName: "SampleData.xls", 
            isUploading: false,
            progress: 0,
            intervalId: null,
            alertMessage: "",
            alertClass: ""
        };
    },

    methods: {
        async uploadData() {
            this.isUploading = true;
            this.progress = 0;
            this.alertMessage = "";
            this.startProgressSimulation();

            let formData = new FormData();
            formData.append("fileName", this.fileName);

            try {
                const response = await fetch("/api/files/import", {
                    method: "POST",
                    body: formData,
                    headers: {
                        "Accept": "application/json"
                    }
                });

                if (response.ok) {
                    this.progress = 100;
                    clearInterval(this.intervalId);
                    setTimeout(() => {
                        this.showAlert("Sample file processed successfully!", "alert-success");
                        this.isUploading = false;
                    }, 500);
                } else {
                    const errorMessage = await response.text();
                    this.showAlert("Error: " + errorMessage, "alert-danger");
                    this.resetProgress();
                }
            } catch (error) {
                this.showAlert("Error processing file: " + error.message, "alert-danger");
                this.resetProgress();
            }
        },

        startProgressSimulation() {
            this.intervalId = setInterval(() => {
                if (this.progress < 90) {
                    this.progress += 10;
                }
            }, 300);
        },

        resetProgress() {
            this.isUploading = false;
            this.progress = 0;
            clearInterval(this.intervalId);
        },

        showAlert(message, type) {
            this.alertMessage = message;
            this.alertClass = type;
            
        }
    },

    style: `
        <style scoped>
            .container {
                max-width: 500px;
                border: 1px solid #ddd;
            }
            .progress {
                height: 25px;
            }
            .progress-bar {
                font-weight: bold;
            }
            .btn-lg {
                padding: 12px 24px;
                font-size: 18px;
            }
        </style>
    `
};
