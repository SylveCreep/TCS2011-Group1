<template>
    <nav aria-label="Page navigation example" class="float-right">
        <ul class="pagination pg-blue">
            <li class="page-item">
                <a href="#" class="page-link" tabindex="-1"
                   v-on:click.prevent="changePage(1)" v-if="pagination.lastPage > 5">
                    <span aria-hidden="true"> 
                    First
                    </span>
                </a>
            </li>
            <li class="page-item" v-if="pagination.currentPage > 1">
                <a href="#" class="page-link" tabindex="-1"
                   v-on:click.prevent="changePage(pagination.currentPage - 1)">
                    <span aria-hidden="true">Previous</span>
                </a>
            </li>
            <li class="page-item" v-for="page in pagesNumber" :key="page"
                :class="{'active': page == pagination.currentPage}">
                <a href="#" class="page-link" v-on:click.prevent="changePage(page)">{{page}}</a>
            </li>
            <li class="page-item" v-if="pagination.currentPage < pagination.lastPage">
                <a href="#" class="page-link" v-on:click.prevent="changePage(pagination.currentPage + 1)">
                    <span aria-hidden="true">Next</span>
                </a>
            </li>
            <li class="page-item">
                <a href="#" class="page-link" tabindex="-1"
                   v-on:click.prevent="changePage(pagination.lastPage)" v-if="pagination.lastPage > 3">
                    Last
                </a>
            </li>
        </ul>
    </nav>
</template>
<script>
export default {
    props: {
        pagination: {}
    },
    computed: {
        pagesNumber() {
            var from;
            var pageArray = [];
            if (this.pagination.lastPage < 5){
                for (from = 1; from <= this.pagination.lastPage; from++){
                    pageArray.push(from);
                }
            }
            else if (this.pagination.currentPage <= 3) {
                for (from = 1; from <= 5; from++) {
                    pageArray.push(from);
                }
            }
            else if (this.pagination.currentPage >= this.pagination.lastPage - 2) {
                for (from =this.pagination.lastPage - 4 ; from <= this.pagination.lastPage; from++) {
                    pageArray.push(from);
                }
            }
            else
            {
                for (from =this.pagination.currentPage - 2 ; from <= this.pagination.currentPage + 2; from++) {
                    pageArray.push(from);
                }
            }
            return pageArray;
        }
    },
    methods: {
        changePage(page){
            this.$emit("currentPage", page);
        },
    }
}
</script>
