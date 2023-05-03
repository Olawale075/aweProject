import { useState } from "react";
import { Container, Form, Button, Row, Col } from "react-bootstrap";
import {useNavigate} from 'react-router-dom'
const initialValues = {
    username : '',
    password : '',

}


const Login = () => {
  const [formDisplay, setFormDisplay] = useState({login : true, register : false});
  const [values, setValues] = useState(initialValues)
  const navigate = useNavigate()
  const switchForm = (e) => {
    const {dataset : {form}} = e.target
    setFormDisplay({
        ...values, [form] : true
    })
  };
  const handleChange = (e) => {
    const { name, value } = e.target;
    setValues({
      ...values,
      [name]: value,
    });
  };
  const handleLogin = (e) => {
    e.preventDefault();
    const url = 'http://146.70.88.25:8082/api/auth'
    fetch(url, {
      headers : {
        'accept' : '*',
        'Content-Type' : 'application/json',

      },
      method : 'POST',
      body : JSON.stringify({username : values.username, password : values.password})
    })
    .then(res => res.json())
    .then(response => {
      const {data} = response
      localStorage.setItem('user', JSON.stringify(data))
      return navigate('/home')

    })
    .catch(err => console.log(err))
  }
  return (
    <Container className=''>
      <Row className='pt-5'>
        {formDisplay.login && (
            <Col className="login mx-auto" sm={8} md={6} lg={5}>
            <h2>Login</h2>
          <Form onSubmit={handleLogin}>
            <Row>
              <Col>
                <Form.Group className="mb-3">
                  <Form.Label>Username</Form.Label>
                  <Form.Control name="username" onChange={handleChange} value={values.username}  type="text"/>
                </Form.Group>
                <Form.Group className="mb-3">
                  <Form.Label>Password</Form.Label>
                  <Form.Control name="password" onChange={handleChange} value={values.password}  type="password"/>
                </Form.Group>
              </Col>
            </Row>
            <Button variant="dark" type="submit">
              Login
            </Button>
            <Container className="text-center border rounded my-2 py-2">
                <p>New User? <span onClick={switchForm} data-form='register'>Signup</span></p>
            </Container>
          </Form>
        </Col>
        )}
        {formDisplay.register && (
            <Col className="register mx-auto" sm={8} md={6} lg={5}>
            <h2>Register</h2>
          <Form>
            <Row>
              <Col>
              <Form.Group className="mb-3">
                  <Row>
                    <Col>
                        <Form.Group className="">
                            <Form.Label>First Name</Form.Label>
                            <Form.Control name="email" onChange={handleChange} value={values.email}  type="email"/>
                        </Form.Group>
                    </Col>
                    <Col>
                        <Form.Group className="">
                            <Form.Label>First Name</Form.Label>
                            <Form.Control name="email" onChange={handleChange} value={values.email}  type="email"/>
                        </Form.Group>
                    </Col>
                  </Row>
                </Form.Group>
                <Form.Group className="mb-3">
                  <Form.Label>Email</Form.Label>
                  <Form.Control name="email" onChange={handleChange} value={values.email}  type="email"/>
                </Form.Group>
                <Form.Group className="mb-3">
                  <Form.Label>Password</Form.Label>
                  <Form.Control name="password" onChange={handleChange} value={values.password}  type="password"/>
                </Form.Group>
              </Col>
            </Row>
            <Button variant="dark" type="submit">
              Register
            </Button>
            <Container className="text-center border rounded my-2 py-2">
            <p>Already a User? <span onClick={switchForm} data-form='login'>Login</span></p>
            </Container>
          </Form>
        </Col>
        )}
      </Row>
    </Container>
  );
};

export default Login;
