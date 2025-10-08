using { legacy as db } from '../db/schema';

service LegacyService {
  entity Customers as projection on db.Customers;
  entity Orders    as projection on db.Orders;
}
