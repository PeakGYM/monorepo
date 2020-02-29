// module RangeCalendar = {
//   [@react.component] [@bs.module "rc-calendar/es/RangeCalendar"]
//   external make:
//     (
//       ~showDateInput: bool=?,
//       ~className: string,
//       ~showToday: bool=?,
//       ~renderFooter: unit => React.element,
//       ~renderSidebar: unit => React.element,
//       ~showWeekNumber: bool=?,
//       ~dateInputPlaceholder: (string, string)
//     ) =>
//     React.element =
//     "default";
// };

// module RangePicker = {
//   type props = {. "value": array(MomentRe.Moment.t)};

//   [@react.component] [@bs.module "rc-calendar/es/Picker"]
//   external make:
//     (
//       ~_open: bool=?,
//       ~align: Js.t({..}),
//       ~dropdownClassName: string=?,
//       ~placement: [@bs.string] [
//                     | `left
//                     | `right
//                     | `bottom
//                     | `top
//                     | `topLeft
//                     | `topRight
//                     | `bottomLeft
//                     | `bottomRight
//                   ],
//       ~value: Time.Period.range,
//       ~onChange: Time.Period.range => unit,
//       ~calendar: React.element,
//       ~onOpenChange: bool => unit,
//       ~children: props => React.element,
//       ~showOk: bool=?
//     ) =>
//     React.element =
//     "default";
// };

// module DatePicker = {
//   type value = Js.Nullable.t(MomentRe.Moment.t);
//   type props = {. "value": value};

//   [@react.component] [@bs.module "rc-calendar/es/Picker"]
//   external make:
//     (
//       ~_open: bool=?,
//       ~align: Js.t({..}),
//       ~dropdownClassName: string=?,
//       ~placement: [@bs.string] [
//                     | `left
//                     | `right
//                     | `bottom
//                     | `top
//                     | `topLeft
//                     | `topRight
//                     | `bottomLeft
//                     | `bottomRight
//                   ],
//       ~value: value,
//       ~onChange: value => unit,
//       ~calendar: React.element,
//       ~onOpenChange: bool => unit,
//       ~children: props => React.element,
//       ~showOk: bool=?
//     ) =>
//     React.element =
//     "default";
// };

// module TimePicker = {
//   // const timePickerElement = <TimePickerPanel defaultValue={moment('00:00:00', 'HH:mm:ss')} />;
//   // defaultValue={moment('00:00:00', 'HH:mm:ss')}
//   [@react.component] [@bs.module "rc-time-picker/lib/Panel"]
//   external make:
//     (~defaultValue: MomentRe.Moment.t=?, ~showSecond: bool=?, ~showMinute: bool=?) => React.element =
//     "default";
// };

// module Calendar = {
//   [@react.component] [@bs.module "rc-calendar"]
//   // ~_open: bool=?,
//   // ~align: Js.t({..}),
//   // ~dropdownClassName: string=?,
//   // ~placement: [@bs.string] [
//   //               | `left
//   //               | `right
//   //               | `bottom
//   //               | `top
//   //               | `topLeft
//   //               | `topRight
//   //               | `bottomLeft
//   //               | `bottomRight
//   //             ],
//   // ~value: Time.Period.range,
//   // ~onChange: Time.Period.range => unit,
//   // ~calendar: React.element,
//   // ~onOpenChange: bool => unit,
//   // ~children: props => React.element,
//   // ~showOk: bool=?
//   external make:
//     (
//       ~className: string=?,
//       ~format: string=?,
//       ~disabledDate: MomentRe.Moment.t => bool=?,
//       ~disabledTime: Js.Nullable.t(MomentRe.Moment.t) => 'a=?,
//       ~timePicker: React.element=?,
//       ~showTime: bool=?,
//       ~locale: 'b=?
//     ) =>
//     React.element =
//     "default";
// };

[@react.component] [@bs.module "antd"]
external make:
  (
    ~dateCellRender: float => React.element,
    ~format: string=?,
    ~disabledDate: MomentRe.Moment.t => bool=?,
    ~disabledTime: Js.Nullable.t(MomentRe.Moment.t) => 'a=?,
    ~timePicker: React.element=?,
    ~showTime: bool=?,
    ~locale: 'b=?
  ) =>
  React.element =
  "Calendar";

module D = {
  [@react.component] [@bs.module "antd"]
  external make:
    (
      ~format: string=?,
      ~disabledDate: MomentRe.Moment.t => bool=?,
      ~disabledTime: Js.Nullable.t(MomentRe.Moment.t) => 'a=?,
      ~timePicker: React.element=?,
      ~showTime: bool=?,
      ~locale: 'b=?
    ) =>
    React.element =
    "Select";
};