/** @format */
import React, { useEffect, useState } from 'react';
import Table from 'react-bootstrap/Table';
import Message from '../api/smsMessage';

function TableComponents() {
  const [status, setStatus] = useState(null);

  useEffect(() => {
    getAllStatus();
  }, []);

  const getAllStatus = () => {
    Message.getAllStatus()
      .then((response) => {
        setStatus(response.data.status);
        console.log("rep1", response.data);
        console.log("string", status);
      })
      .catch((error) => {
        console.log(error);
      });
  }
  return (
    <Table striped bordered hover>
      <thead>
        <tr>
          <th>S/N</th>
          <th>Receiver</th>
          <th>Receiver Number</th>
          <th>Message</th>
          <th>Delivery Status</th>
          <th>Message Type</th>
          <th>Provider</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          {status?.map((page) => (
            <>
              <td key={page.id}>{page.id}</td>
              <td>Tobi</td>
              <td>08000</td>
              <td key={page}>{page.message}</td>
              <td key={page}>{page.deliveryStatus}</td>
              <td>BALANCE ENQUIRY</td>
              <td>MTN</td>
            </>
          ))}
          {/* <td>1</td> */}
        </tr>
        <tr>
          <td>2</td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
        </tr>
        <tr>
          <td>3</td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
        </tr>
      </tbody>
    </Table>
  );
}

export default TableComponents;
