/** @format */
import React, { useEffect, useState } from 'react';

import Dropdown from 'react-bootstrap/Dropdown';
import Message from '../api/smsMessage';
import Template from '../api/smsTemplate';

function Dropdowns() {
  const [tempStatus, setTempStatus] = useState(null);
  const [smsType, setSmsType] = useState(null);

  useEffect(() => {
    getAllTemplateStatus();
    getAllTemplateSmsType();
  }, []);

  const getAllTemplateStatus = () => {
    Message.getAllTemplateStatus()
      .then((response) => {
        setTempStatus(response.data);
        console.log(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
  };
  const getAllTemplateSmsType = () => {
    Template.getAllTemplateSmsType()
      .then((response) => {
        setSmsType(response.data);
        console.log(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const handleStatus = () => {
   console.log("clo")
  }

  return (
    <>
      <Dropdown>
        <Dropdown.Toggle id="dropdown-button-dark-example1" variant="secondary">
          Message Type
        </Dropdown.Toggle>

        <Dropdown.Menu variant="dark">
          {smsType?.map((smstype) => (
            <Dropdown.Item key={smstype.value}>{smstype.value}</Dropdown.Item>
          ))}
        </Dropdown.Menu>
      </Dropdown>

      <Dropdown>
        <Dropdown.Toggle id="dropdown-button-dark-example1" variant="secondary">
          Delivery Status
        </Dropdown.Toggle>

        <Dropdown.Menu variant="dark">
          {tempStatus?.map((status) => (
            <Dropdown.Item key={status.value}>{status.value}</Dropdown.Item>
          ))}
        </Dropdown.Menu>
      </Dropdown>
    </>
  );
}

export default Dropdowns;
