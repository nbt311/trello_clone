import './App.css';
import Router from "./Route/Router";
import Login from "./Components/Login";
import Logout from "./Components/logout";

function App() {
  return (
    <div className="App">
      <Router/>
     <Login/>
    </div>
  );
}

export default App;
