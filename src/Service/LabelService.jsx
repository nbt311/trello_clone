import axios from "axios";

const API_URL = "http://localhost:8080/api/test/labels/";

class LabelService{
    searchLabel(lableId) {
        return axios.get(`${API_URL}${lableId}/allCards`)
    }
}
export default new LabelService();