<!--<template>-->
    <!--<div class='menu-wrapper'>-->

        <!--&lt;!&ndash;<template v-for="(item,i) in routes">&ndash;&gt;-->
        <!--&lt;!&ndash;<template v-if="item.children">&ndash;&gt;-->
        <!--&lt;!&ndash;<el-submenu :index="item.path+'/'+item.children[0].path">&ndash;&gt;-->
        <!--&lt;!&ndash;<template slot="title"><i :class="item.icon ? item.icon : ''"></i>{{item.name}}</template>&ndash;&gt;-->


        <!--&lt;!&ndash;<template v-for="(subItem,i1) in item.children">&ndash;&gt;-->
        <!--&lt;!&ndash;<template v-if="subItem.children">&ndash;&gt;-->
        <!--&lt;!&ndash;<el-submenu :index="item.path + '/' + subItem.path + '/' + subItem.children[0].path">&ndash;&gt;-->
        <!--&lt;!&ndash;<template slot="title"><i :class="subItem.icon ? subItem.icon : ''"></i>{{subItem.name}}</template>&ndash;&gt;-->

        <!--&lt;!&ndash;<template v-if="subItem.children">&ndash;&gt;-->
        <!--&lt;!&ndash;<el-menu-item v-for="(subItemItem,i2) in subItem.subs" :key="i2" :index="item.path + '/' + subItem.path + '/' + subItemItem.path">&ndash;&gt;-->
        <!--&lt;!&ndash;<template slot="title"><i :class="subItemItem.icon ? subItemItem.icon : ''"></i>{{subItemItem.name}}</template>&ndash;&gt;-->
        <!--&lt;!&ndash;</el-menu-item>&ndash;&gt;-->
        <!--&lt;!&ndash;</template>&ndash;&gt;-->

        <!--&lt;!&ndash;</el-submenu>&ndash;&gt;-->
        <!--&lt;!&ndash;</template>&ndash;&gt;-->

        <!--&lt;!&ndash;<template v-else>&ndash;&gt;-->
        <!--&lt;!&ndash;<el-menu-item :index="item.path + '/' + subItem.path">&ndash;&gt;-->
        <!--&lt;!&ndash;<i :class="subItem.icon ? subItem.icon : ''"></i>{{ subItem.name }}&ndash;&gt;-->
        <!--&lt;!&ndash;</el-menu-item>&ndash;&gt;-->
        <!--&lt;!&ndash;</template>&ndash;&gt;-->
        <!--&lt;!&ndash;</template>&ndash;&gt;-->

        <!--&lt;!&ndash;</el-submenu>&ndash;&gt;-->
        <!--&lt;!&ndash;</template>&ndash;&gt;-->
        <!--&lt;!&ndash;<template v-else>&ndash;&gt;-->
        <!--&lt;!&ndash;<el-menu-item :index="item.path">&ndash;&gt;-->
        <!--&lt;!&ndash;<template slot="title"><i :class="item.icon ? item.icon : ''"></i>{{item.name}}</template>&ndash;&gt;-->
        <!--&lt;!&ndash;</el-menu-item>&ndash;&gt;-->
        <!--&lt;!&ndash;</template>&ndash;&gt;-->
        <!--&lt;!&ndash;</template>&ndash;&gt;-->

        <!--<template v-for="(item,i) in routes">-->
            <!--<template v-if="item.children">-->
                <!--<el-submenu :index="item.path+'/'+item.children[0].path">-->
                    <!--<template slot="title"><i :class="item.icon ? item.icon : ''"></i>{{item.path}}-{{item.name}}</template>-->

                    <!--<template v-for="subChild in item.children">-->
                        <!--<sidebar-item :routes='[subChild]'></sidebar-item>-->
                    <!--</template>-->

                <!--</el-submenu>-->
            <!--</template>-->
            <!--<template v-else>-->
                <!--<el-menu-item :index="item.path">-->
                    <!--<template slot="title"><i :class="item.icon ? item.icon : ''"></i>{{item.path}}-{{item.name}}</template>-->
                <!--</el-menu-item>-->
            <!--</template>-->
        <!--</template>-->


    <!--</div>-->
<!--</template>-->

<!--<script>-->
    <!--export default {-->
        <!--name: 'SidebarItem',-->
        <!--props: {-->
            <!--routes: {-->
                <!--type: Array-->
            <!--}-->
        <!--}-->
    <!--}-->
<!--</script>-->

<template>
    <div v-if="!item.hidden&&item.children">
        <router-link v-if="!item.hidden&&item.noDropdown&&!item.children[0].children" :to="item.path+'/'+item.children[0].path">
            <el-menu-item class="submenu-title-noDropdown" :index="item.path+'/'+item.children[0].path">
                <icon-svg v-if='item.icon' :icon-class="item.icon"></icon-svg>
                <span slot="title">{{item.name}}</span>
            </el-menu-item>
        </router-link>

        <el-submenu class="nest-menu" :index="item.path" v-else-if="!item.noDropdown&&!item.hidden">
            <template slot="title">
                <icon-svg v-if='item.icon' :icon-class="item.icon"></icon-svg>
                <span v-if='item.name' slot="title">{{item.name}}</span>
            </template>
            <template v-for="child in item.children" v-if='!child.hidden'>

                <sidebar-item :key="child.path" v-if='child.children&&child.children.length>0' :item='child'> </sidebar-item>

                <router-link :key="child.path" v-else :to="item.path+'/'+child.path">
                    <el-menu-item :index="item.path+'/'+child.path">
                        <icon-svg v-if='child.icon' :icon-class="child.icon"></icon-svg>
                        <span slot="title">{{child.name}}</span>
                    </el-menu-item>
                </router-link>

            </template>

        </el-submenu>
    </div>
</template>

<script>
export default {
    name: "SidebarItem",
    props: {
        item: {}
    },
    methods: {
        // 查找子节点是否有可显示的节点
        hasFilterChildrenHidden(children) {
            const showingChildren = children.filter(item => {
                return !item.hidden;
            });
            return showingChildren.length === 1;
        }
    }
};
</script>
