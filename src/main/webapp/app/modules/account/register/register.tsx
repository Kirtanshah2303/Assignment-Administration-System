import React, { useState, useEffect } from 'react';
import { Translate, translate, ValidatedField, ValidatedForm, isEmail } from 'react-jhipster';
import { Row, Col, Alert, Button } from 'reactstrap';
import { toast } from 'react-toastify';

import PasswordStrengthBar from 'app/shared/layout/password/password-strength-bar';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { handleRegister, reset } from './register.reducer';
import { getDropdownMenuPlacement } from 'react-bootstrap/DropdownMenu';
import value from '*.json';

export const RegisterPage = () => {
  const [password, setPassword] = useState('');
  const [role, setRole] = useState('');
  const dispatch = useAppDispatch();

  useEffect(
    () => () => {
      dispatch(reset());
    },
    []
  );

  const currentLocale = useAppSelector(state => state.locale.currentLocale);

  const handleValidSubmit = ({ username, email, firstPassword, authority }) => {
    console.log('ROLES are --> ' + authority);
    authority = [authority];
    console.log('New --> ' + authority);
    dispatch(handleRegister({ login: username, email, password: firstPassword, langKey: currentLocale, authorities: authority }));
  };

  const updatePassword = event => setPassword(event.target.value);

  const successMessage = useAppSelector(state => state.register.successMessage);

  useEffect(() => {
    if (successMessage) {
      toast.success(translate(successMessage));
    }
  }, [successMessage]);

  const handleChange = e => {
    setRole(e.target.value);
    console.log('ROLE is --> ' + role);
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h1 id="register-title" data-cy="registerTitle">
            <Translate contentKey="register.title">Registration</Translate>
          </h1>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          <ValidatedForm id="register-form" onSubmit={handleValidSubmit}>
            <ValidatedField
              name="username"
              label={translate('global.form.username.label')}
              placeholder={translate('global.form.username.placeholder')}
              validate={{
                required: { value: true, message: translate('register.messages.validate.login.required') },
                pattern: {
                  value: /^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$/,
                  message: translate('register.messages.validate.login.pattern'),
                },
                minLength: { value: 1, message: translate('register.messages.validate.login.minlength') },
                maxLength: { value: 50, message: translate('register.messages.validate.login.maxlength') },
              }}
              data-cy="username"
            />
            <ValidatedField
              name="email"
              label={translate('global.form.email.label')}
              placeholder={translate('global.form.email.placeholder')}
              type="email"
              validate={{
                required: { value: true, message: translate('global.messages.validate.email.required') },
                minLength: { value: 5, message: translate('global.messages.validate.email.minlength') },
                maxLength: { value: 254, message: translate('global.messages.validate.email.maxlength') },
                validate: v => isEmail(v) || translate('global.messages.validate.email.invalid'),
              }}
              data-cy="email"
            />
            <ValidatedField
              name="firstPassword"
              label={translate('global.form.newpassword.label')}
              placeholder={translate('global.form.newpassword.placeholder')}
              type="password"
              onChange={updatePassword}
              validate={{
                required: { value: true, message: translate('global.messages.validate.newpassword.required') },
                minLength: { value: 4, message: translate('global.messages.validate.newpassword.minlength') },
                maxLength: { value: 50, message: translate('global.messages.validate.newpassword.maxlength') },
              }}
              data-cy="firstPassword"
            />
            <PasswordStrengthBar password={password} />
            <ValidatedField
              name="secondPassword"
              label={translate('global.form.confirmpassword.label')}
              placeholder={translate('global.form.confirmpassword.placeholder')}
              type="password"
              validate={{
                required: { value: true, message: translate('global.messages.validate.confirmpassword.required') },
                minLength: { value: 4, message: translate('global.messages.validate.confirmpassword.minlength') },
                maxLength: { value: 50, message: translate('global.messages.validate.confirmpassword.maxlength') },
                validate: v => v === password || translate('global.messages.error.dontmatch'),
              }}
              data-cy="secondPassword"
            />
            <label>Profiles</label>

            {/* <select className="form-control" defaultValue="ROLE_STUDENT" id="authority" name="authority" onChange={handleChange} form="register-form">*/}
            {/*  <option value="ROLE_STUDENT">ROLE_STUDENT</option>*/}
            {/*  <option value="ROLE_FACULTY">ROLE_FACULTY</option>*/}
            {/* </select>*/}

            <ValidatedField name="authority" type={'select'} defaultValue={['ROLE_STUDENT']}>
              <option value="ROLE_STUDENT">ROLE_STUDENT</option>
              <option value="ROLE_FACULTY">ROLE_FACULTY</option>
            </ValidatedField>

            <Button style={{ margin: '23px' }} id="register-submit" color="primary" type="submit" data-cy="submit">
              <Translate contentKey="register.form.button">Register</Translate>
            </Button>
          </ValidatedForm>
          <p>&nbsp;</p>
          <Alert color="warning">
            <span>
              <Translate contentKey="global.messages.info.authenticated.prefix">If you want to </Translate>
            </span>
            <a className="alert-link">
              <Translate contentKey="global.messages.info.authenticated.link"> sign in</Translate>
            </a>
            <span>
              <Translate contentKey="global.messages.info.authenticated.suffix">
                , you can try the default accounts:
                <br />- Administrator (login=&quot;admin&quot; and password=&quot;admin&quot;)
                <br />- User (login=&quot;user&quot; and password=&quot;user&quot;).
              </Translate>
            </span>
          </Alert>
        </Col>
      </Row>
    </div>
  );
};

export default RegisterPage;
