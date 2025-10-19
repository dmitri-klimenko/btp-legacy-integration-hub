namespace legacy;

entity Customers {
  key ID     : UUID;
  Name       : String(100);
  Country    : String(50);
}

entity Orders {
  key ID     : UUID;
  CustomerID : Association to Customers;
  Amount     : Decimal(10,2);
  Currency   : String(3);
  Status     : String(20);
  CreatedAt  : Timestamp @cds.on.insert : $now;
  Notes      : String(255);
}
