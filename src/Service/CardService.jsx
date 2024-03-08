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

    changeCardTitle(cardId,title) {
        const data = JSON.stringify({ title: title }); // Chuyển đổi dữ liệu thành chuỗi JSON

        return axios.put(`${API_URL}${cardId}`, data, {
            headers: {
                'Content-Type': 'application/json' // Đảm bảo bạn thiết lập header để cho backend biết rằng dữ liệu gửi đi là JSON
            }
        }).then(response => {
            return response.data;
        });
    }

}
export default new CardService();