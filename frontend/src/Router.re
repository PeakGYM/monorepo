type t =
  | Main
  | Schedule
  | DayLog(string)
  | DayEdit(string)
  | Pick(string)
  | SetupVisit(string)
  | Statistics
  | Map;

let toRoute = (v: ReasonReactRouter.url) => {
  let {path, _}: ReasonReactRouter.url = v;

  switch (path) {
  | ["schedule"] => Schedule
  | ["trainings", day, "log"] => DayLog(day)
  | ["trainings", day, "edit"] => DayEdit(day)
  | ["pick", day] => Pick(day)
  | ["map"] => Map
  | ["setup-visit", coachId] => SetupVisit(coachId)
  | ["statistics"] => Statistics
  | _ => Main
  };
};

let toUrl =
  fun
  | Main => "/"
  | Schedule => {j|/schedule|j}
  | DayLog(day) => {j|/trainings/$day/log|j}
  | DayEdit(day) => {j|/trainings/$day/edit|j}
  | Pick(day) => {j|/pick/$day|j}
  | SetupVisit(coachId) => {j|/setup-visit/$coachId|j}
  | Statistics => {j|/statistics|j}
  | Map => "/map";

let go = route => route->toUrl->ReasonReactRouter.push;

let useRoute = () => {
  let url = ReasonReactRouter.useUrl();

  url->toRoute;
};
