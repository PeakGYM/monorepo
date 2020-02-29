type t =
  | Main
  | Schedule
  | Day(string)
  | Map;

let toRoute = (v: ReasonReactRouter.url) => {
  let {path, _}: ReasonReactRouter.url = v;

  switch (path) {
  | ["schedule"] => Schedule
  | ["trainings", day] => Day(day)
  | ["map"] => Map
  | _ => Main
  };
};

let toUrl =
  fun
  | Main => "/"
  | Schedule => {j|/schedule|j}
  | Day(day) => {j|/trainings/$day|j}
  | Map => "/map";

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