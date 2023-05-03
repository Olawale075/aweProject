import React from 'react'
import { Card, CardGroup,Button, Row, Col } from 'react-bootstrap'
import { Link } from 'react-router-dom';
import background from "./Bubu style/bub1.jfif";
import backgrounds from "./Man style/sokoto.jfif";
import backgroundss from "./Gown style/gown3.jfif";
import backgroundsss from "./blous/blous3.jfif";
export const Home = () => {
  return (

 <>
    <Card className='text-center m-1'> <p>Specialist in high quality Latest Design in Ladies&Mans Wears <br></br>Made to Mesure in Africa in Customs</p> </Card>  
    <CardGroup>
      <Card>
        <Card.Img variant="top" src={background} />
        <Card.Body>
          <Card.Title>Latest Bubu-Style</Card.Title>
          <Card.Text>
Get more style by click on the buttom      </Card.Text>
<Link to={"/bubu"} className="btn btn-primary">click</Link>
        </Card.Body>
       
      </Card>
      <Card>
        <Card.Img variant="top" src={backgrounds} />
        <Card.Body>
        <Card.Title>Latest Mans-Wear</Card.Title>
          <Card.Text>
Get more style by click on the buttom      </Card.Text>
<Link to={"/manwear"} className="btn btn-primary">click</Link>
        </Card.Body>
      
      </Card>
      <Card>
      <Card.Img variant="top" src={backgroundss} />
        <Card.Body>
        <Card.Title>Latest Gown for Ladies</Card.Title>
          <Card.Text>
Get more style by click on the buttom      </Card.Text>
<Link to={"/gown"} className="btn btn-primary">click</Link>
        </Card.Body>
     
      </Card>
      <Card>
      <Card.Img variant="top" src={backgroundsss} />
        <Card.Body>
        <Card.Title>Latest Gown for Ladies</Card.Title>
          <Card.Text>
Get more style by click on the buttom      </Card.Text>
<Link to={"/blouse"} className="btn btn-primary">click</Link>
        </Card.Body>
     
      </Card>
    </CardGroup>    
    </>
  )
}
