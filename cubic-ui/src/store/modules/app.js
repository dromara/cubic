import { getStore, setStore } from "../../utils/store";
import * as types from "../mutation-types";

const state = {
    sidebar: {
        opened: !+getStore("sidebarStatus")
    },
    visitedViews: []
};

// getters
const getters = {
    sidebar: state => state.sidebar,
    visitedViews: state => state.visitedViews
};

// actions
const actions = {
    ToggleSideBar({ commit }) {
        commit(types.TOGGLE_SIDEBAR);
    },
    ShowSideBar({ commit }) {
        commit(types.SHOW_SIDEBAR);
    },
    addVisitedViews({ commit }, view) {
        commit(types.ADD_VISITED_VIEWS, view);
    },
    delVisitedViews({ commit, state }, view) {
        return new Promise(resolve => {
            commit(types.DEL_VISITED_VIEWS, view);
            resolve([...state.visitedViews]);
        });
    }
};

// mutations
const mutations = {
    [types.TOGGLE_SIDEBAR](state) {
        if (state.sidebar.opened) {
            setStore("sidebarStatus", 1, 365);
        } else {
            setStore("sidebarStatus", 0, 365);
        }
        state.sidebar.opened = !state.sidebar.opened;
    },
    [types.SHOW_SIDEBAR](state) {
        if (state.sidebar.opened) {
            setStore("sidebarStatus", 1, 365);
        }
        state.sidebar.opened = false;
    },
    [types.ADD_VISITED_VIEWS](state, view) {
        if (state.visitedViews.some(v => v.path === view.path)) return;
        state.visitedViews.push({ name: view.name, path: view.path });
    },
    [types.DEL_VISITED_VIEWS](state, view) {
        let index;
        for (const [i, v] of state.visitedViews.entries()) {
            if (v.path === view.path) {
                index = i;
                break;
            }
        }
        state.visitedViews.splice(index, 1);
    }
};

export default {
    state,
    getters,
    actions,
    mutations
};
