export default function weekUserReducer(state = {}, action) {
    return Object.assign({}, state, {
        userInfo : Object.assign({}, state.userInfo)
    });
}