import React, { Component } from "react";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import "./App.css";
import { Home } from "./components/Home";
import "bootstrap/dist/css/bootstrap.min.css";
import { About } from "./components/About";
import Contact from "./components/Contact";

import HeaderComponent from "./components/HeaderComponent";
import FooterComponent from "./components/FooterComponent";
import { Layout } from "./components/Layout";
import { Cards } from "./components/Cards";
import OffcanvasExample from "./components/OffcanvasExample";
import { ManWears } from "./components/ManWears";
import { Bubu, BubuDress } from "./components/BubuDress";
import { Gown } from "./components/Gown";
import { Blouse } from "./components/Blouse";
import { FreeGown } from "./components/FreeGown";


class App extends Component {
  render() {
    return (
      <React.Fragment>
       <OffcanvasExample/>
        <Cards/>
        <Layout>
          <Router >
            <div className="container">
              <Switch>
                <Route exact path="/" component={Home}></Route>
                <Route  path="/home" component={Home}></Route>
                <Route path="/contact" component={Contact}></Route>
                <Route path="/about" component={About}></Route>
                <Route path="/manwear" component={ManWears}></Route>
                <Route path="/bubu" component={BubuDress}></Route> 
                <Route path="/free" component={FreeGown}></Route>
                <Route path="/gown" component={Gown}></Route>
                <Route path="/blouse" component={Blouse}></Route>

                 </Switch>
                        
            </div>
          </Router>
        </Layout>{" "}
        <FooterComponent />
      </React.Fragment>
    );
  }
}
export default App;
