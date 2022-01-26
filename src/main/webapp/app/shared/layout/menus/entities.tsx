import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { Translate, translate } from 'react-jhipster';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    data-cy="entity"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <>{/* to avoid warnings when empty */}</>
    <MenuItem icon="asterisk" to="/course">
      <Translate contentKey="global.menu.entities.course" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/course-category">
      <Translate contentKey="global.menu.entities.courseCategory" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/course-enrollment">
      <Translate contentKey="global.menu.entities.courseEnrollment" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/course-level">
      <Translate contentKey="global.menu.entities.courseLevel" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/course-session-progress">
      <Translate contentKey="global.menu.entities.courseSessionProgress" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/course-assignment-progress">
      <Translate contentKey="global.menu.entities.courseAssignmentProgress" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/course-review-status">
      <Translate contentKey="global.menu.entities.courseReviewStatus" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/course-section">
      <Translate contentKey="global.menu.entities.courseSection" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/course-session">
      <Translate contentKey="global.menu.entities.courseSession" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/course-assignment">
      <Translate contentKey="global.menu.entities.courseAssignment" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/course-assignment-input">
      <Translate contentKey="global.menu.entities.courseAssignmentInput" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/course-assignment-output">
      <Translate contentKey="global.menu.entities.courseAssignmentOutput" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/course-type">
      <Translate contentKey="global.menu.entities.courseType" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
