
// export default {
//     template: `
//         <div class="d-flex flex-column border-end bg-light" style="min-height: 100vh;">
//             <!-- Toggle Button Always Visible at the Top -->
//             <div class="d-flex justify-content-start p-2">
//                 <button class="btn btn-outline-black" @click="toggleSidebar">
//                     <i class="bi bi-square-half"></i>
//                 </button>
//             </div>
            
//             <!-- Sidebar -->
//             <aside :class="['d-flex flex-column p-3', sidebarOpen ? 'd-block' : 'd-none']" style="width: 200px; min-height: 100vh;">
// 				<button class="btn btn-outline-secondary w-100 mb-2" @click="$emit('change-view', 'data_import')">Import Data</button>
//                 <button class="btn btn-outline-primary w-100 mb-2" @click="$emit('change-view', 'main1')">Show Main 1</button>
              
//             </aside>
//         </div>
//     `,
//     data() {
//         return {
//             sidebarOpen: true
//         };
//     },
//     methods: {
//         toggleSidebar() {
//             this.sidebarOpen = !this.sidebarOpen;
//         }
//     }
// };


export default {
    template: `
        <div class="d-flex flex-column border-end bg-light" style="min-height: 100vh;">
            <!-- Toggle Button Always Visible at the Top -->
            <div class="d-flex justify-content-start p-2">
                <button class="btn btn-outline-black" @click="toggleSidebar">
                    <i class="bi bi-square-half"></i>
                </button>
            </div>
            
            <!-- Sidebar -->
            <aside :class="['d-flex flex-column p-3', sidebarOpen ? 'w-100' : 'w-auto']" 
                style="min-height: 100vh; width: 100px; transition: width 0.3s;">
                
                <button class="btn btn-secondary w-100 mb-2 d-flex align-items-center"
                    @click="$emit('change-view', 'data_import')">
                    <i class="bi bi-file-earmark-arrow-up"></i>
                    <span class="ms-2" v-if="sidebarOpen">Import Data</span>
                </button>

                <button class="btn btn-primary w-100 mb-2 d-flex align-items-center"
                    @click="$emit('change-view', 'main1')">
                    <i class="bi bi-file-earmark-text"></i>
                    <span class="ms-2" v-if="sidebarOpen">Show Main 1</span>
                </button>

            </aside>
        </div>
    `,
    data() {
        return {
            sidebarOpen: true
        };
    },
    methods: {
        toggleSidebar() {
            this.sidebarOpen = !this.sidebarOpen;
        }
    }
};

