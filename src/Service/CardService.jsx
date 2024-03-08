import axios from "axios";
import workspace from "../Pages/WorkspacePage/Workspace";
import workspaceSidebar from "../Components/SideBar/WorkspaceSidebar";

const API_URL = "http://localhost:8080/api/cards/";

class CardService {
    createNewCard(title, boardId, columnId) {
        return axios.post(API_URL + "create", {
            title: title,
            boardId: boardId,
            columnId: columnId
        })
    }

}
export default new CardService();