XSD's totalDigits facet specifies the maximum number of digits (decimal or integer, before and after the decimal point, not counting the decimal point itself) allowed for a numeric datatype.

JSON Schema does NOT have a direct equivalent to totalDigits.

This is a common point of difference when converting from XML Schema (XSD) to JSON Schema. JSON Schema provides keywords for validating numbers, but they work differently:

type: number (for decimals or integers) or integer (for whole numbers).

minimum / maximum / exclusiveMinimum / exclusiveMaximum: For defining the range of the number.

multipleOf: To specify that a number must be a multiple of a given value (useful for controlling decimal precision in some cases, but not totalDigits).

Why the difference?
XSD is strongly tied to XML's structured data model, which includes precise numeric types and facets. totalDigits is very specific to defining the exact precision of numbers, often important for financial or scientific data representation in XML.

JSON is a more lightweight, flexible data format, and JSON Schema reflects this. While it allows for basic numeric validation, it doesn't provide a built-in mechanism for constraining the total number of digits across the whole number.

How to achieve similar validation in JSON Schema (workarounds):
Since there's no direct equivalent, you generally have a few options, depending on your exact requirements and tolerance for complexity:

Use type: "string" and pattern (Regular Expressions):
This is the most common and robust workaround if you truly need to enforce totalDigits. You would treat the numeric value as a string in your JSON, and then use a regular expression to validate its format.

XSD Example:

XML

<xs:simpleType name="MyTotalDigitsType">
  <xs:restriction base="xs:decimal">
    <xs:totalDigits value="5"/>
  </xs:restriction>
</xs:simpleType>
This would allow numbers like 123.4, 12.34, 12345, 0.1234 (all have 5 total digits).

JSON Schema Equivalent (using string and pattern):

JSON

{
  "type": "string",
  "pattern": "^-?(\\d{1,5}|\\d{1,4}\\.\\d|\\d{1,3}\\.\\d{2}|\\d{1,2}\\.\\d{3}|\\d\\.\\d{4}|0\\.\\d{4,4})$"
}
Explanation of the regex:

^-?: Optional negative sign.

(...): Group for the main number patterns.

\\d{1,5}: Allows 1 to 5 digits for integers (e.g., 12345).

|: OR operator.

\\d{1,4}\\.\\d: 1 to 4 digits before decimal, then 1 digit after (e.g., 1234.5).

\\d{1,3}\\.\\d{2}: 1 to 3 digits before decimal, then 2 digits after (e.g., 123.45).

...and so on for other combinations of digits before and after the decimal point to sum up to 5 total digits.

0\\.\\d{4,4}: Handles cases like 0.1234 explicitly.

Challenges with this approach:

The value is stored as a string in JSON, not a true number, which might require parsing in consuming applications.

The regular expression can become very complex for large totalDigits values or if combined with fractionDigits.

It's generally recommended to store numeric values as JSON numbers unless there's a strong reason not to (like leading zeros being significant, which is also an XSD string concept).

Combine minimum and maximum keywords:
You can try to infer the range from totalDigits. For example, if totalDigits is 3, the number could range from -999 to 999.

XSD Example (totalDigits = 3 for an integer):

XML

<xs:simpleType name="MyIntegerType">
  <xs:restriction base="xs:integer">
    <xs:totalDigits value="3"/>
  </xs:restriction>
</xs:simpleType>
JSON Schema Equivalent:

JSON

{
  "type": "integer",
  "minimum": -999,
  "maximum": 999
}
Limitations:

This only works for integers or when fractionDigits is also strictly defined.

It doesn't truly validate the "total digits" concept, as 99.9 would technically fall within this range if it were a number type, but only has 3 total digits.

It doesn't handle decimals with specific total digits effectively.

Custom Keywords (Validator-Specific):
Some JSON Schema validators allow you to define custom keywords and provide your own validation logic (e.g., ajv in JavaScript). This would allow you to implement totalDigits behavior directly. However, this makes your schema non-standard and not portable to other validators.

In summary:

The JSON equivalent of xsd:totalDigits is typically achieved by changing the JSON type to "string" and then using the pattern keyword with a carefully constructed regular expression. This is because JSON Schema's native numeric validation keywords (minimum, maximum, multipleOf) do not provide a direct way to constrain the total number of digits in a numeric value.
