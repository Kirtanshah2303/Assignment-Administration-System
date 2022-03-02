import React, { useEffect, useState } from 'react';
import { ValidatedField, ValidatedForm, isEmail } from 'react-jhipster';
import { Button, Alert, Col, Row } from 'reactstrap';
import { toast } from 'react-toastify';

// import { handlePasswordResetInit, reset } from '../password-reset.reducer';
// import { useAppDispatch, useAppSelector } from 'app/config/store';

export const findUsername = () => {
  const [email, setEmail] = useState('');

  let successMessage;

  const changeHandler2 = event => {
    setEmail(event.target.value);
  };

  const handleValidSubmit = () => {
    // dispatch(handlePasswordResetInit(email));
    const formData = new FormData();

    formData.append('email', email);

    fetch('/api/account/findUsername', {
      method: 'POST',
      body: formData,
    })
      .then(response => response.json())
      .then(result => {
        successMessage = 'Username will be sent to your email , stay tuned..!!';
        toast.success(successMessage);
        console.log('Success:', result);
      })
      .catch(error => {
        console.error('Error:', error);
      });
  };

  // useEffect(() => {
  //   if (successMessage) {
  //     toast.success(successMessage);
  //   }
  // }, [successMessage]);

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h1>Get your Username</h1>
          <Alert color="warning">
            <p>Enter the email address you used to register</p>
          </Alert>
          <ValidatedField
            name="email"
            label="Email"
            placeholder={'Your email'}
            type="email"
            onChange={changeHandler2}
            validate={{
              required: { value: true, message: 'Your email is required.' },
              pattern: {
                value: /^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[charusat.edu.in|charusat.ac.in]+$/,
                message: 'Your email is invalid , Use CHARUSAT EMAIL-ID ONLY.',
              },
              minLength: { value: 5, message: 'Your email is required to be at least 5 characters.' },
              maxLength: { value: 254, message: 'Your email cannot be longer than 50 characters.' },
              validate: v => isEmail(v) || 'Your email is invalid.',
            }}
            data-cy="emailResetPassword"
          />
          <Button color="primary" onClick={handleValidSubmit}>
            Get Username
          </Button>
        </Col>
      </Row>
    </div>
  );
};

export default findUsername;
