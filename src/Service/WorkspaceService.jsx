import axios from "axios";

const API_URL = "http://localhost:8080/api/workspaces/";

class WorkspaceService {
    getAll() {
        return axios.get(API_URL + "all")
            .then(response => {
                localStorage.setItem("workspacelist", JSON.stringify(response.data));
                return response.data;
            }).catch(error => {
                throw error;
            });
    }
    getBoardByWorkspaceId() {

    }
    getWorkspaceByUser() {
        return axios.get()
    }
}
export default new WorkspaceService();