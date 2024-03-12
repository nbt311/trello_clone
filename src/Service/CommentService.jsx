import axios from "axios";

const API_URL = "http://localhost:8080/api/comments/";

class CommentService {
    createNewComment(content ,cardId, userId) {
        return axios.post(`${API_URL}create/${cardId}/${userId}`, {
            content: content
        })
    }
}
export default new CommentService();