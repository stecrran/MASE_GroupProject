const app = Vue.createApp({
    data() {
        return {
            currentView: 'data_import' // Default view
        };
    },
    methods: {
        changeView(newView) {
            this.currentView = newView;
        }
    }
});

// Function to automatically load and register components
async function loadComponents() {
    const componentFiles = [
        "navbar.js",
        "aside_menu.js",
        "main1.js",
        "data_import.js"
    ];

    for (const file of componentFiles) {
        const module = await import(`./components/${file}`);
        const componentName = file.replace(".js", ""); // Remove ".js" to get component name
        app.component(componentName, module.default || module); // Register component
    }

    // Mount Vue AFTER all components are loaded
    app.mount("#app");
}

// Call the function to load components
loadComponents();
