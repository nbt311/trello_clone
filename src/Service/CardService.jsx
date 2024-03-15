import axios from "axios";
import workspace from "../Pages/WorkspacePage/Workspace";
import workspaceSidebar from "../Components/SideBar/WorkspaceSidebar";
import {date} from "yup";

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

    addMemberToCard(cardId,member){
        return axios.post(`${API_URL}${cardId}/members `,{
           username:member
        })
    }

    showMemberToCard(cardId){
        return axios.get(`${API_URL}${cardId}/members`)
    }

    showLabelToCard(cardId){
        return axios.get(`${API_URL}${cardId}/allLabels`)
    }

    addLabelToCard(cardId, labelId){
        return axios.post(`${API_URL}${cardId}/newLabels/${labelId}`)
    }

    showCommentToCard(cardId){
        return axios.get(`${API_URL}${cardId}/comment`)
    }

    removeLabelToCard(cardId, labelId){
        return axios.delete(`${API_URL}${cardId}/removeLabels/${labelId}`)
    }

    getAttachmentUrl(cardId){
        return axios.get(`${API_URL}${cardId}/attachment`)
    }

    updateAttachmentUrl(cardId, data){
        return axios.post(`${API_URL}${cardId}/attachment`, data)
    }

    getCardByQuery(boardId,query){
        return axios.get(`${API_URL}${boardId}/suggest/${query}`)
    }

}
export default new CardService();