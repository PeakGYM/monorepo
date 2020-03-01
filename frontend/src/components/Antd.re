module Input = {
  [@react.component] [@bs.module "antd"]
  external make:
    (
      ~placeholder: string,
      ~value: string=?,
      ~onChange: ReactEvent.Synthetic.t => unit
    ) =>
    React.element =
    "Input";
};

module Select = {
  [@react.component] [@bs.module "antd"]
  external make:
    (
      ~placeholder: string,
      ~value: 't=?,
      ~style: ReactDOMRe.Style.t=?,
      ~showSearch: bool=?,
      ~optionLabelProp: string=?,
      ~onChange: string => unit,
      ~onSearch: string => unit,
      ~options: array('a)=?,
      ~filterOption: bool=?,
      ~styles: ReactDOMRe.Style.t=?,
      ~children: React.element=?
    ) =>
    React.element =
    "Select";

  module Option = {
    [@react.component] [@bs.module "antd"] [@bs.scope "Select"]
    external make:
      (
        ~key: string=?,
        ~label: string=?,
        ~value: 't=?,
        ~styles: ReactDOMRe.Style.t=?,
        ~children: React.element=?
      ) =>
      React.element =
      "Option";
  };
};

module Spin = {
  [@react.component] [@bs.module "antd"]
  external make: (~spinning: bool=?, ~size: string=?) => React.element =
    "Spin";
};