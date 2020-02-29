type t =
  | Main
<<<<<<< HEAD
  | Schedule
  | Day(string);
=======
  | Map
  | Schedule(string);
>>>>>>> 0abb0c10952fe51fa39f3d3923469d4eaf122487

let toRoute = (v: ReasonReactRouter.url) => {
  let {path, _}: ReasonReactRouter.url = v;

  switch (path) {
<<<<<<< HEAD
  | ["schedule"] => Schedule
  | ["trainings", day] => Day(day)
=======
  | ["schedules", id] => Schedule(id)
  | ["map"] => Map
>>>>>>> 0abb0c10952fe51fa39f3d3923469d4eaf122487
  | _ => Main
  };
};

let toUrl =
  fun
  | Main => "/"
<<<<<<< HEAD

  | Schedule => {j|/schedule|j}
  | Day(day) => {j|/trainings/$day|j};
=======
  | Map => "/map"
  | Schedule(id) => {j|/schedules/$id|j};
>>>>>>> 0abb0c10952fe51fa39f3d3923469d4eaf122487

// let toKey =
//   fun
//   | TransferInListInternal => "0"
//   | TransferInListExternal => "1"
//   | TransferInSettle => "2"
//   | TransferOutListInternal => "3"
//   | TransferOutListExternal => "4"
//   | TransferOutSettle => "5"
//   | TransferOutPlanned => "6"
//   | InternalTransferList => "7"
//   | Balances => "8"
//   | Modifications => "9"
//   | Users => "10"
//   | Settings => "11"
//   | _ => "-1";

// let fromKey =
//   fun
//   | "0" => Some(TransferInListInternal)
//   | "1" => Some(TransferInListExternal)
//   | "2" => Some(TransferInSettle)
//   | "3" => Some(TransferOutListInternal)
//   | "4" => Some(TransferOutListExternal)
//   | "5" => Some(TransferOutSettle)
//   | "6" => Some(TransferOutPlanned)
//   | "7" => Some(InternalTransferList)
//   | "8" => Some(Balances)
//   | "9" => Some(Modifications)
//   | "10" => Some(Users)
//   | "11" => Some(Settings)
//   | _ => None;

let go = route => route->toUrl->ReasonReactRouter.push;

let useRoute = () => {
  let url = ReasonReactRouter.useUrl();

  url->toRoute;
};
