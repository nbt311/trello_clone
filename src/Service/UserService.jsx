import axios from "axios";

const API_URL = "http://localhost:8080/api/users/";

class UserService {
    getWorkspaceByUser(userId) {
        return axios.get(`${API_URL}${userId}/workspaces`)
            .then(response => {
                localStorage.setItem("workspacelist", JSON.stringify(response.data.ownedWorkspaces));
                return response.data.ownedWorkspaces;
            }).catch(error => {
                throw error;
            });
    }

    getUserById(userId) {
        return axios.get(`${API_URL}${userId}/workspaces`).then(response => {
            localStorage.setItem("user", JSON.stringify(response.data))
            return response.data
        }).catch(error => {
            throw error;
        })
    }
    getBoardByWorkspaceId() {

    }

}
export default new UserService();