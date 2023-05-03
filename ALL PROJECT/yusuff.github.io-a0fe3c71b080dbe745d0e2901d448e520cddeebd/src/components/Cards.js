import React, { Component,useState, useEffect } from "react";
import { Container, Button,Row,Col,Card,CardImg} from "react-bootstrap";
import background from "./blouse/my1.png";
import backgrounds from "./blouse/my.png"
export class Cards extends Component {
  
  constructor() {
    super();
    this.state = {
      sport: "",
    };
  }
  myArrany = ["Programer", "WebDeveloper", "SoftwearDeveloper"];
  componentDidMount() {
    setInterval(() => {
      this.setState({
        sport: this.myArrany[Math.floor(Math.random() * this.myArrany.length)],
      });
    }, 2000);
  }
  render() {
    return (
      <div>
        {" "}
        <div className="mt-1" >
          <div
            class=" container-flud p-1  mt-1 style={{ backgroundImage: `url(${background}) rounded-2"
            style={{
              backgroundImage: `url(${background})`,
              backgroundRepeat: "no-repeat fixed botton",
              backgroundSize: "cover",
              backgroundPosition: "center",
              minHeight: "82vh",
          

              IDBIndex: "-2",
            }}
          >
         
          </div>   <Card className='text-center text-end'>
    <p className="text-center p-1 m-1"><h3>Hello
   <h6>I'M Alh. Olawale Yussuf CEO ALSALAM HOME OF FASHION</h6></h3></p> </Card> 
       </div>
      </div>
    );
  }
}

export default Cards;
