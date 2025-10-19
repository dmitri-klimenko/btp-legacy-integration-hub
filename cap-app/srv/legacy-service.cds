using { legacy as db } from '../db/schema';

service LegacyService @(requires: 'authenticated-user') {
  
  @odata.draft.enabled
  @(restrict: [
    { grant: 'READ', to: 'Viewer' },
    { grant: '*', to: 'Admin' }  // Admin can do everything
  ])
  entity Customers as projection on db.Customers;
  
  @odata.draft.enabled
  @(restrict: [
    { grant: 'READ', to: 'Viewer' },
    { grant: '*', to: 'Admin' }  // Admin can do everything
  ])
  entity Orders as projection on db.Orders;
}
