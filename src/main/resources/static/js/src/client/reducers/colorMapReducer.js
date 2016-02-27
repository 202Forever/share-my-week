import weekUserReducer from './weekUserReducer';

export default function colorMapReducer(state = {}, action) {
    if (state.colorMap) {
        let users = state.weekData.entity.users;
        let colorMap = {};
        if (users) {
            users.forEach((user) => {colorMap[user.color] = weekUserReducer(user)});
        }
        return Object.assign({}, state, {
            colorMap: Object.assign({}, state.colorMap, colorMap)
        });
    }
    return state;
}