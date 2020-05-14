<template>
    <div class='tabs-view-container'>
        <span v-for="tag in Array.from(visitedViews)" :key="tag.path" class="tag-box" @contextmenu.prevent="closeViewTabs(tag, $event)">
            <router-link class="tabs-view"  :to="tag.path" :key="tag.path">
                <el-tag :closable="true" :type="isActive(tag.path) ? '' : 'info'" @close='closeViewTabs(tag,$event)'>
                    {{tag.name}}
                </el-tag>
            </router-link>
        </span>
    </div>

</template>

<script>
export default {
    computed: {
        visitedViews() {
            return this.$store.state.app.visitedViews.slice(-6);
        }
    },
    methods: {
        parentMenuShow() {
            // 父级的是否显示左边菜单
            this.$emit("listenChildParentMenuHide");
        },
        closeViewTabs(view, $event) {
            this.$store.dispatch("delVisitedViews", view).then(views => {
                if (this.isActive(view.path)) {
                    const latestView = views.slice(-1)[0];
                    if (latestView) {
                        this.$router.push(latestView.path);
                    } else {
                        this.$router.push("/");
                    }
                }
            });
            if ($event) {
                $event.preventDefault();
            }
        },
        generateRoute() {
            if (this.$route.name) {
                return this.$route;
            }
            return false;
        },
        addViewTabs() {
            const route = this.generateRoute();
            if (!route) {
                return false;
            }
            this.parentMenuShow();
            this.$store.dispatch("addVisitedViews", this.generateRoute());
        },
        isActive(path) {
            return path === this.$route.path;
        }
    },
    watch: {
        $route() {
            this.addViewTabs();
        }
    }
};
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.tabs-view-container {
    height: 100%;
    .tag-box {
        margin-left: 10px;
        &:first-child {
            margin-left: 0;
        }
    }
    .tabs-view {
        .el-tag {
            display: inline;
            padding: 10px;
        }
    }
}
</style>
