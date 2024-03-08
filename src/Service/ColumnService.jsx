import axios from "axios";
import workspace from "../Pages/WorkspacePage/Workspace";
import workspaceSidebar from "../Components/SideBar/WorkspaceSidebar";

const API_URL = "http://localhost:8080/api/columns/";

class ColumnService {
    createNewColumn(email, workspaceId, title, boardId) {
        return axios.post(API_URL + "create", {
            email: email,
            workspaceId: workspaceId,
            title: title,
            boardId: boardId
        })
    }

    updateColumnDetail(columnId, updateData) {
        return axios.put(`${API_URL}${columnId}`, updateData).then(response => {
            return response.data
        })
    }

    getListCard(columnId) {
        return axios.get(`${API_URL}${columnId}/cards`)
    }

    movingCardToDifferentColumnAPI(updateData) {
        return axios.put(`${API_URL}supports/moving-card` , updateData).then(response => {
            return response.data
        })
    }

}
export default new ColumnService();