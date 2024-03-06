import axios from "axios";
import workspace from "../Pages/WorkspacePage/Workspace";
import workspaceSidebar from "../Components/SideBar/WorkspaceSidebar";

const API_URL = "http://localhost:8080/api/boards/";

class BoardService {
    getListColumn(boardId) {
        return axios.get(`${API_URL}${boardId}/columns`)
    }
}
export default new BoardService();