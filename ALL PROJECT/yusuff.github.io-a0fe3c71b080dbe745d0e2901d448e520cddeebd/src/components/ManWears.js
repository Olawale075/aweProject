import React from "react";
import { Card, CardGroup, Button } from "react-bootstrap";
import s1 from "./Man style/sokoto7.jfif";
import s2 from "./Man style/sokoto9.jfif";
import s3 from "./Man style/sokoto10.jfif";
import s4 from "./Man style/sokoto11.jfif";
import s5 from "./Man style/sokoto12.jfif";
import s6 from "./Man style/sokoto13.jfif";
import s7 from "./Man style/sokoto8.jfif";
import s8 from "./Man style/sokoto15.jfif";
import s9 from "./Man style/sokoto16.jfif";
import s10 from "./Man style/sokoto17.jfif";
import s11 from "./Man style/sokoto18.jfif";
import s13 from "./Man style/sokoto20.jfif";
import s14 from "./Man style/sokoto29.jfif";
import s15 from "./Man style/sokoto.jfif";
import s16 from "./Man style/sokoto2.jpg";
export const ManWears = () => {
  return (
    <div className="">
      <Card className="text-center m-1 container-fluid">
        {" "}
        <p>
          Specialist in high quality Latest Design in Ladies&Mans Wears{" "}
          <br></br>Made to Mesure in Africa in Customs
        </p>{" "}
      </Card>
      <CardGroup>
        <Card>
          <Card.Img variant="top" src={s15} />
          <Card.Body>
            <Card.Title>Latest Man style </Card.Title>
          </Card.Body>
        </Card>
        <Card>
          <Card.Img variant="top" src={s7} />
          <Card.Body>
            <Card.Title>Latest Usex-Wear</Card.Title>
          </Card.Body>
        </Card>
        <Card>
          <Card.Img variant="top" src={s1} />
          <Card.Body>
            <Card.Title>Latest Man style</Card.Title>
          </Card.Body>
        </Card>
      </CardGroup>
      <CardGroup>
        <Card>
          <Card.Img variant="top" src={s2} />

          <Card.Body>
            <Card.Title>Latest Man style</Card.Title>
          </Card.Body>
        </Card>{" "}
        <Card className="m-1 p-3">
          <Card.Img variant="top" src={s3} />

          <Card.Body>
            <Card.Title>Latest Man style</Card.Title>
          </Card.Body>
        </Card>{" "}
        <Card>
          <Card.Img variant="top" src={s10} />

          <Card.Body>
            <Card.Title>Latest Man style</Card.Title>
          </Card.Body>
        </Card>{" "}
      </CardGroup>
      <CardGroup>
        <Card>
          <Card.Img variant="top" src={s9} />

          <Card.Body>
            <Card.Title>Latest Man style</Card.Title>
          </Card.Body>
        </Card>{" "}
        <Card>
          <Card.Img variant="top" src={s8} />

          <Card.Body>
            <Card.Title>Latest Man style</Card.Title>
          </Card.Body>
        </Card>{" "}
        <Card>
          <Card.Img variant="top" src={s6} />

          <Card.Body>
            <Card.Title>Latest Man style</Card.Title>
          </Card.Body>
        </Card>    </CardGroup><CardGroup>
        <Card>
          <Card.Img variant="top" src={s5} />

          <Card.Body>
            <Card.Title>Latest Man style</Card.Title>
          </Card.Body>
        </Card>{" "}
        <Card>
          <Card.Img variant="top" src={s4} />

          <Card.Body>
            <Card.Title>Latest Man style</Card.Title>
          </Card.Body>
        </Card>
        <Card>
          <Card.Img variant="top" src={s13} />

          <Card.Body>
            <Card.Title>Latest Man style</Card.Title>
          </Card.Body>
        </Card>    </CardGroup><CardGroup>
        <Card>
          <Card.Img variant="top" src={s14} />

          <Card.Body>
            <Card.Title>Latest Man style</Card.Title>
          </Card.Body>
        </Card>{" "}
        <Card>
          <Card.Img variant="top" src={s11} />

          <Card.Body>
            <Card.Title>Latest Man style</Card.Title>
          </Card.Body>
        </Card>
        <Card>
          <Card.Img variant="top" src={s16} />

          <Card.Body>
            <Card.Title>Latest Man style</Card.Title>
          </Card.Body>
        </Card>
      </CardGroup>
    </div>
  );
};
